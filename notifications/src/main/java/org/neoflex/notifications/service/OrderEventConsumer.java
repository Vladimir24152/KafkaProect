package org.neoflex.notifications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neoflex.notifications.model.OrderSentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "sent_orders",
            groupId = "notifications-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderSentEvent event) {
        log.info("Received ORDER_SENT: orderId={}, tracking={}",
                event.orderId(), event.trackingNumber());

        try {
            notificationService.sendDeliveryNotification(event);
        } catch (Exception e) {
            log.error("Notification failed for order {}: {}",
                    event.orderId(), e.getMessage(), e);
            throw e;
        }
    }
}