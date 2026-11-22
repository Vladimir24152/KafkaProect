package org.neoflex.shipping.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neoflex.shipping.model.OrderPayedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final ShippingService shippingService;

    @KafkaListener(
            topics = "payed_orders",
            groupId = "shipping-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderPayedEvent event) {
        log.info("Received ORDER_PAYED: orderId={}, paymentId={}",
                event.orderId(), event.paymentId());

        try {
            shippingService.processShipping(event);
        } catch (Exception e) {
            log.error("Shipping failed for order {}: {}",
                    event.orderId(), e.getMessage(), e);
            throw e;
        }
    }
}