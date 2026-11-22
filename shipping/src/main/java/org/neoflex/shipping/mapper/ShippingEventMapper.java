package org.neoflex.shipping.mapper;

import org.neoflex.shipping.model.OrderPayedEvent;
import org.neoflex.shipping.model.OrderSentEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ShippingEventMapper {

    public OrderSentEvent toSentEvent(OrderPayedEvent payed) {
        return new OrderSentEvent(
                "ORDER_SENT",
                payed.orderId(),
                payed.customerId(),
                "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                "COURIER",
                Instant.now()
        );
    }
}