package dev.bhargav.ecommerce.service;

import dev.bhargav.ecommerce.customer.CustomerClient;
import dev.bhargav.ecommerce.dto.OrderRequest;
import dev.bhargav.ecommerce.dto.OrderResponse;
import dev.bhargav.ecommerce.entity.OrderLine;
import dev.bhargav.ecommerce.exception.BusinessException;
import dev.bhargav.ecommerce.kafka.OrderConfirmation;
import dev.bhargav.ecommerce.kafka.OrderProducer;
import dev.bhargav.ecommerce.mapper.OrderMapper;
import dev.bhargav.ecommerce.payment.PaymentClient;
import dev.bhargav.ecommerce.payment.PaymentRequest;
import dev.bhargav.ecommerce.product.ProductClient;
import dev.bhargav.ecommerce.product.PurchaseResponse;
import dev.bhargav.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final CustomerClient customerClient;

    private final PaymentClient paymentClient;

    private final ProductClient productClient;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest orderRequest) {
        // Validate customer existence
        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order: No customer found with ID " + orderRequest.customerId()));

        // Validate & purchase products
        List<PurchaseResponse> purchasedProducts;
        try {
            purchasedProducts = productClient.purchaseProducts(orderRequest.products());
        } catch (Exception e) {
            throw new BusinessException("Failed to purchase products: " + e.getMessage());
        }

        // Calculate total amount (server-side)
        BigDecimal totalAmount = purchasedProducts.stream()
                .map(p -> p.price().multiply(BigDecimal.valueOf(p.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        var order = orderMapper.toOrder(orderRequest);
        order.setTotalAmount(totalAmount);

        // Set order lines
        var orderLines = orderRequest.products().stream()
                .map(p -> OrderLine.builder()
                        .productId(p.productId())
                        .quantity(p.quantity())
                        .order(order) // bi-directional reference
                        .build())
                .collect(Collectors.toList());

        order.setOrderLines(orderLines);

        // Save the order with lines (CascadeType.ALL assumed)
        var savedOrder = orderRepository.save(order);

        // Call payment client
        paymentClient.requestOrderPayment(new PaymentRequest(
                orderRequest.totalAmount(),
                orderRequest.paymentMethod(),
                savedOrder.getId(),
                savedOrder.getReference(),
                customer
        ));

        // Publish order confirmation
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                savedOrder.getReference(),
                savedOrder.getTotalAmount(),
                savedOrder.getPaymentMethod(),
                customer,
                purchasedProducts
        ));

        return savedOrder.getId();
    }


    public List<OrderResponse> findAllOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(this.orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.orderRepository.findById(id)
                .map(this.orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
