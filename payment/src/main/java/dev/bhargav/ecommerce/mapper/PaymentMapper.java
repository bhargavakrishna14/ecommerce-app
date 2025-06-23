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

        return Payment.builder()
                .id(paymentRequest.id())
                .amount(paymentRequest.amount())
                .paymentMethod(paymentRequest.paymentMethod())
                .orderId(paymentRequest.orderId())
                .build();
    }

}
