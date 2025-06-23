package dev.bhargav.ecommerce.dto;

public record OrderLineRequest(
        Integer productId,
        double quantity
) {
}
