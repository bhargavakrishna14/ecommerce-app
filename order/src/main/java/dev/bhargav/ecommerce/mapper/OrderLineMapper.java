package dev.bhargav.ecommerce.mapper;

import dev.bhargav.ecommerce.dto.OrderLineRequest;
import dev.bhargav.ecommerce.dto.OrderLineResponse;
import dev.bhargav.ecommerce.entity.Order;
import dev.bhargav.ecommerce.entity.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest request) {
        if (request == null) {
            return null;
        }

        return OrderLine.builder()
                .id(request.id())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        if (orderLine == null) {
            return null;
        }

        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }

}
