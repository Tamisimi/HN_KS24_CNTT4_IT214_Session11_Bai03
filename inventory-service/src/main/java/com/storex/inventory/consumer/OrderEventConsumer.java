package com.storex.inventory.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Inventory consumer — group-id: inventory-group (application.yml).
 * Nhiều instance cùng group → chia partition (scale).
 */
@Component
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    @KafkaListener(topics = "${app.kafka.order-topic}", groupId = "inventory-group")
    public void onOrderCreated(String message) {
        log.info("[Inventory] Nhận order.created — trừ kho. payload={}", message);
        // Xử lý trừ kho (mô phỏng)
    }
}
