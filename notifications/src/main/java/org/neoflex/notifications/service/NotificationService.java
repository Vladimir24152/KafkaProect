package org.neoflex.notifications.service;

import lombok.extern.slf4j.Slf4j;
import org.neoflex.notifications.model.OrderSentEvent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendDeliveryNotification(OrderSentEvent event) {
        log.info("Sending delivery notification to customer={} for order={} tracking={} carrier={}",
                event.customerId(),
                event.orderId(),
                event.trackingNumber(),
                event.carrier());

        if (event.customerId() == null || event.customerId().isBlank()) {
            throw new IllegalArgumentException(
                    "Cannot send notification: customerId is empty for order " + event.orderId());
        }

        log.info("Notification sent successfully for order={}", event.orderId());
    }
}