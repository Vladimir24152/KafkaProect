package org.neoflex.payment.mapper;

import org.neoflex.payment.model.OrderCreatedEvent;
import org.neoflex.payment.model.OrderPayedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class PaymentEventMapper {

    public OrderPayedEvent toPayedEvent(OrderCreatedEvent order) {
        return new OrderPayedEvent(
                "ORDER_PAYED",
                order.orderId(),
                order.customerId(),
                order.amount(),
                UUID.randomUUID().toString(),
                Instant.now()
        );
    }
}