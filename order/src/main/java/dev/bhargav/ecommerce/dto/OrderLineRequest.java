package dev.bhargav.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderLineRequest(
        Integer id,

        @NotNull(message = "Order ID is required")
        Integer orderId,

        @NotNull(message = "Product ID is required")
        Integer productId,

        @Positive(message = "Quantity must be greater than zero")
        BigDecimal quantity
) {
}
