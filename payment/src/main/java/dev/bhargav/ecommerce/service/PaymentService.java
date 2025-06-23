package dev.bhargav.ecommerce.service;

import dev.bhargav.ecommerce.dto.PaymentRequest;
import dev.bhargav.ecommerce.mapper.PaymentMapper;
import dev.bhargav.ecommerce.notification.NotificationProducer;
import dev.bhargav.ecommerce.notification.PaymentNotificationRequest;
import dev.bhargav.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = this.paymentRepository
                .save(this.paymentMapper.toPayment(paymentRequest));

        this.notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstname(),
                        paymentRequest.customer().lastname(),
                        paymentRequest.customer().email()
                )
        );
        return payment.getId();
    }

}
