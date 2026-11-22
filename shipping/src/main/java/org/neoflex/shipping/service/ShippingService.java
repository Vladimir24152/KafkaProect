package org.neoflex.shipping.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neoflex.shipping.mapper.ShippingEventMapper;
import org.neoflex.shipping.model.OrderPayedEvent;
import org.neoflex.shipping.model.OrderSentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private static final String TOPIC = "sent_orders";

    private final KafkaTemplate<String, OrderSentEvent> kafkaTemplate;
    private final ShippingEventMapper mapper;

    public void processShipping(OrderPayedEvent payed) {
        log.info("Processing shipping for order {}", payed.orderId());

        if (payed.amount() == null || payed.amount().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid payed amount for order " + payed.orderId());
        }

        OrderSentEvent sent = mapper.toSentEvent(payed);

        kafkaTemplate.send(TOPIC, sent.orderId(), sent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send SENT event for order {}: {}",
                                sent.orderId(), ex.getMessage(), ex);
                    } else {
                        log.info("Sent order {} tracking={} -> partition {} offset {}",
                                sent.orderId(),
                                sent.trackingNumber(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}