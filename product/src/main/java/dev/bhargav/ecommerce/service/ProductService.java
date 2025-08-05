package dev.bhargav.ecommerce.service;

import dev.bhargav.ecommerce.dto.ProductPurchaseRequest;
import dev.bhargav.ecommerce.dto.ProductPurchaseResponse;
import dev.bhargav.ecommerce.dto.ProductRequest;
import dev.bhargav.ecommerce.dto.ProductResponse;
import dev.bhargav.ecommerce.exception.ProductPurchaseException;
import dev.bhargav.ecommerce.mapper.ProductMapper;
import dev.bhargav.ecommerce.repository.CategoryRepository;
import dev.bhargav.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    public Integer createProduct(ProductRequest productRequest) {
        var category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID:: " + productRequest.categoryId()));

        var product = productMapper.toProduct(productRequest, category);
        return productRepository.save(product).getId();
    }

    public ProductResponse findById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + id));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = productRepository.findByIdInOrderById(productIds);

        if (storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var sortedRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();

        for (int i=0; i<storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);

            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock for product: " + product.getName());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);

            purchasedProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        // Save all updated products in one batch
        productRepository.saveAll(storedProducts);

        return purchasedProducts;
    }
}
