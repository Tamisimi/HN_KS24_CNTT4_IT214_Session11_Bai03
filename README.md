# Bài 3 Session 11 — Fan-out & Event Consumer

## Mô hình

```
order.created → Topic: storex-order-events
                      │
          ┌───────────┴───────────┐
          ▼                       ▼
group: inventory-group    group: loyalty-group
  Inventory-Service         Loyalty-Service
  (3 instances chia tải)    (cộng điểm)
```

- **Fan-out:** mỗi service một **group-id khác nhau** → cả hai nhận **100%** message.
- **Scale Inventory:** 3 instance cùng `group-id: inventory-group` → Kafka chia partition → mỗi instance ~33% nếu topic ≥ 3 partitions.

## BUG-04 — Phân tích

Nếu cả Inventory và Loyalty đặt `group-id: storex-system` (chung):
- Kafka coi chúng là **cùng một consumer group**.
- Mỗi message chỉ giao cho **một** consumer trong group.
- Hậu quả: đơn được trừ kho thì **không** tới Loyalty (hoặc ngược lại) → mất cộng điểm / mất trừ kho.

**Sửa:** mỗi business service một group riêng:
- Inventory: `inventory-group`
- Loyalty: `loyalty-group`

## REQ-01 — Số Partition tối thiểu

Với **3 instance** Inventory trong cùng group:
- Số partition **≥ 3** (khuyến nghị đúng 3 hoặc nhiều hơn).
- Vì: trong một group, số consumer active xử lý song song **không vượt quá** số partition.
- Topic đề bài đã có **5 partitions** → đủ cho 3 instance Inventory.

## Chạy

1. Kafka + topic `storex-order-events` (5 partitions).
2. Chạy `loyalty-service` (1 instance).
3. Chạy 3 instance `inventory-service` (đổi port 8082/8083/8084).
4. Produce `order.created` (Bài 2) → log cả hai service; 3 inventory chia message.
