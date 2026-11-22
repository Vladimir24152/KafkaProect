package org.neoflex.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neoflex.payment.mapper.PaymentEventMapper;
import org.neoflex.payment.model.OrderCreatedEvent;
import org.neoflex.payment.model.OrderPayedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private static final String TOPIC = "payed_orders";

    private final KafkaTemplate<String, OrderPayedEvent> kafkaTemplate;
    private final PaymentEventMapper mapper;

    public void processPayment(OrderCreatedEvent order) {
        log.info("Processing payment for order {}", order.orderId());

        if (order.amount() == null || order.amount().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid amount for order " + order.orderId() + ": " + order.amount());
        }

        OrderPayedEvent payed = mapper.toPayedEvent(order);

        kafkaTemplate.send(TOPIC, payed.orderId(), payed)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send payed event for order {}: {}",
                                payed.orderId(), ex.getMessage(), ex);
                    } else {
                        log.info("Payed order {} -> partition {} offset {}",
                                payed.orderId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}