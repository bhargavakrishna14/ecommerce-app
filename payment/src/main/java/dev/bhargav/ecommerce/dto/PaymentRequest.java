package dev.bhargav.ecommerce.dto;

import dev.bhargav.ecommerce.entity.Customer;
import dev.bhargav.ecommerce.entity.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(

        Integer id,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Order ID is required")
        Integer orderId,

        @NotNull(message = "Order reference is required")
        String orderReference,

        @Valid
        @NotNull(message = "Customer info is required")
        Customer customer
) {
}
