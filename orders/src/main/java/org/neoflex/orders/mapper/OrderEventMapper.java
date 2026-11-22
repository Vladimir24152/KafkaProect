package org.neoflex.orders.mapper;

import org.neoflex.orders.model.CreateOrderRequest;
import org.neoflex.orders.model.OrderCreatedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrderEventMapper {

    public OrderCreatedEvent toCreatedEvent(CreateOrderRequest request) {
        return new OrderCreatedEvent(
                "ORDER_CREATED",
                UUID.randomUUID().toString(),
                request.customerId(),
                request.productId(),
                request.quantity(),
                request.amount(),
                Instant.now()
        );
    }
}