# Phân tích BUG-04 & REQ-01

## BUG-04: Chung group-id="storex-system"

| | Chung group | Tách group |
|---|-------------|------------|
| group-id | Cả hai = `storex-system` | `inventory-group` / `loyalty-group` |
| Hành vi Kafka | Competing consumers — 1 message → 1 consumer | Fan-out — 1 message → mọi group |
| Kết quả nghiệp vụ | Trừ kho **hoặc** cộng điểm (không đủ cả hai) | Trừ kho **và** cộng điểm |

**Kết luận:** Fan-out bắt buộc **mỗi loại service một `spring.kafka.consumer.group-id` riêng**.

## REQ-01: Partition cho 3 instance Inventory

- Công thức: `số partition ≥ số consumer instance tối đa trong group`.
- 3 instance Inventory → tối thiểu **3 partitions**.
- Topic `storex-order-events` với **5 partitions** (đề bài) → đạt yêu cầu; có thể scale thêm tới 5 instance trong group.
