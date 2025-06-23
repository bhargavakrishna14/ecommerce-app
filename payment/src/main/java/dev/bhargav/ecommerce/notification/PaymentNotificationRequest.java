package dev.bhargav.ecommerce.notification;

import dev.bhargav.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest (

        String orderReference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        String customerFirstname,

        String customerLastname,

        String customerEmail
) {
}
