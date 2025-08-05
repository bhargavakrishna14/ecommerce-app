package dev.bhargav.ecommerce.controller;

import dev.bhargav.ecommerce.dto.PaymentRequest;
import dev.bhargav.ecommerce.dto.PaymentResponse;
import dev.bhargav.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody @Valid PaymentRequest paymentRequest
    ) {
        Integer id = this.paymentService.createPayment(paymentRequest);
        return ResponseEntity.created(URI.create("/api/v1/payments/" + id))
                .body(new PaymentResponse(id));
        }

}
