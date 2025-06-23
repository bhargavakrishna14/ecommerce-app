package dev.bhargav.ecommerce.payment;

import dev.bhargav.ecommerce.customer.CustomerResponse;
import dev.bhargav.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
