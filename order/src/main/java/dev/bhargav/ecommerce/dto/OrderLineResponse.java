package dev.bhargav.ecommerce.dto;

public record OrderLineResponse(
        Integer id,
        Integer productId,
        double quantity
) {

}
