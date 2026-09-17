package com.storex.loyalty.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Loyalty consumer — group-id: loyalty-group (khác inventory).
 * Fan-out: nhận 100% order.created song song với Inventory.
 */
@Component
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    @KafkaListener(topics = "${app.kafka.order-topic}", groupId = "loyalty-group")
    public void onOrderCreated(String message) {
        log.info("[Loyalty] Nhận order.created — cộng điểm. payload={}", message);
        // Xử lý cộng điểm (mô phỏng)
    }
}
