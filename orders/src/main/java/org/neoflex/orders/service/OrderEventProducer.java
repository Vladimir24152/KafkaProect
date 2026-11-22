package org.neoflex.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neoflex.orders.mapper.OrderEventMapper;
import org.neoflex.orders.model.CreateOrderRequest;
import org.neoflex.orders.model.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {

    private static final String TOPIC = "new_orders";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final OrderEventMapper mapper;

    public String sendOrderCreatedEvent(CreateOrderRequest request) {
        OrderCreatedEvent event = mapper.toCreatedEvent(request);

        kafkaTemplate.send(TOPIC, event.orderId(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send order {}: {}", event.orderId(), ex.getMessage(), ex);
                    } else {
                        log.info("Order {} -> partition {} offset {}",
                                event.orderId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });

        return event.orderId();
    }
}