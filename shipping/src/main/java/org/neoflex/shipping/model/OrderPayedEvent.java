package org.neoflex.shipping.model;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderPayedEvent(
        String eventType,
        String orderId,
        String customerId,
        BigDecimal amount,
        String paymentId,
        Instant timestamp
) {}