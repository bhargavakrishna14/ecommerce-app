package dev.bhargav.ecommerce.mapper;

import dev.bhargav.ecommerce.dto.PaymentRequest;
import dev.bhargav.ecommerce.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
        if(paymentRequest == null) {
            return null;
        }

//    Don’t set auditing fields (they’re auto-handled by JPA).

        return Payment.builder()
                .id(paymentRequest.id())
                .amount(paymentRequest.amount())
                .paymentMethod(paymentRequest.paymentMethod())
                .orderId(paymentRequest.orderId())
                .orderReference(paymentRequest.orderReference())
                .build();
    }

}
