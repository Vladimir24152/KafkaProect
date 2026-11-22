# Order Processing System (Kafka Training Project)

Учебный проект для отработки микросервисной архитектуры на Apache Kafka.
**Не предназначен для продакшена.**

## Что это

Четыре Spring Boot микросервиса, связанные асинхронной цепочкой через Kafka:

Orders ──► new_orders ──► Payment ──► payed_orders ──► Shipping ──► sent_orders ──► Notifications


| Сервис | Порт | Читает | Пишет |
|---|---|---|---|
| orders | 8081 | — | `new_orders` |
| payment | 8082 | `new_orders` | `payed_orders` |
| shipping | 8083 | `payed_orders` | `sent_orders` |
| notifications | 8084 | `sent_orders` | — |

## Что демонстрируется

- Параметризуемое партиционирование (`KAFKA_PARTITION_COUNT`)
- Параллельная обработка партиций (consumer group + `concurrency`)
- Балансировка через ключ `orderId` (все события заказа — в одной партиции)
- Retry с экспоненциальной задержкой и Dead Letter Topic (DLT)
- Идемпотентный продюсер (`acks=all`, `enable.idempotence=true`)
- JSON-сериализация событий через Spring Kafka
- Логирование каждого этапа с партицией и offset

## Запуск

```bash
  docker compose up -d --build

```

Проверка
```bash
  curl -X POST http://localhost:8081/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{"customerId":"c1","productId":"p1","quantity":2,"amount":199.99}'