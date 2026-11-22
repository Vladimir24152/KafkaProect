package org.neoflex.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neoflex.payment.model.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "new_orders",
            groupId = "payment-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderCreatedEvent event) {
        log.info("Received ORDER_CREATED: orderId={}, amount={}",
                event.orderId(), event.amount());

        try {
            paymentService.processPayment(event);
        } catch (Exception e) {
            log.error("Payment failed for order {}: {}", event.orderId(), e.getMessage(), e);
            throw e;
        }
    }
}