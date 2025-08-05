package dev.bhargav.ecommerce.mapper;

import dev.bhargav.ecommerce.dto.OrderRequest;
import dev.bhargav.ecommerce.dto.OrderResponse;
import dev.bhargav.ecommerce.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        if (request == null) {
            return null;
        }

        return Order.builder()
                .id(request.id())
                .reference(request.reference())
                .totalAmount(request.totalAmount())
                .paymentMethod(request.paymentMethod())
                .customerId(request.customerId())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId(),
                order.getCreatedDate(),
                order.getLastModifiedDate()
        );
    }

}
