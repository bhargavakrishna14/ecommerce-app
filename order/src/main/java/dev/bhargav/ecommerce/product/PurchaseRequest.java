package dev.bhargav.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record PurchaseRequest(

        @NotNull(message = "Product is mandatory")
        Integer productId,

        @Positive(message = "Quantity is mandatory")
        BigDecimal quantity
) {
}
