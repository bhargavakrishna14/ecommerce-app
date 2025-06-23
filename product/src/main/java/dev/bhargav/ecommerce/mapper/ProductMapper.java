package dev.bhargav.ecommerce.mapper;

import dev.bhargav.ecommerce.dto.ProductPurchaseResponse;
import dev.bhargav.ecommerce.dto.ProductRequest;
import dev.bhargav.ecommerce.dto.ProductResponse;
import dev.bhargav.ecommerce.entity.Category;
import dev.bhargav.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .availableQuantity(productRequest.availableQuantity())
                .price(productRequest.price())
                .category(
                        Category.builder()
                                .id(productRequest.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        var category = product.getCategory();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),

                category != null ? category.getId() : null,
                category != null ? category.getName() : null,
                category != null ? category.getDescription() : null
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
