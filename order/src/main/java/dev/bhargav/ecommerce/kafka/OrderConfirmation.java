package dev.bhargav.ecommerce.kafka;

import dev.bhargav.ecommerce.customer.CustomerResponse;
import dev.bhargav.ecommerce.entity.PaymentMethod;
import dev.bhargav.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
