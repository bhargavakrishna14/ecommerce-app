package dev.bhargav.ecommerce.dto;

import java.math.BigDecimal;

public record OrderLineResponse(
        Integer id,
        BigDecimal quantity
) {

}
