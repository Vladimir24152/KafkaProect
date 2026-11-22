package org.neoflex.shipping.model;

import java.time.Instant;

public record OrderSentEvent(
        String eventType,
        String orderId,
        String customerId,
        String trackingNumber,
        String carrier,
        Instant timestamp
) {}