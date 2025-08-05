package dev.bhargav.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.bhargav.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(Include.NON_EMPTY)
public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String customerId,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {

}
