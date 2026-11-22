package org.neoflex.orders.service;

import lombok.RequiredArgsConstructor;
import org.neoflex.orders.model.CreateOrderRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderEventProducer producer;

    public String createNewOrder(CreateOrderRequest request) {
        return producer.sendOrderCreatedEvent(request);
    }
}
