# Visualization notes (Gateway → Eureka → Load-balanced services)

## Servers
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8080

## Routes through Gateway
- `/products/**` → `lb://PRODUCT-SERVICE`
- `/orders/**` → `lb://ORDER-SERVICE`

## How to demonstrate load balancing
Run **two Product Service instances** (same `spring.application.name=PRODUCT-SERVICE`, different ports):

From `product-service` folder (separate terminals):
```bat
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083
```

Then call gateway:
- `POST http://localhost:8080/products` (create)
- `GET  http://localhost:8080/products` (repeat calls; gateway should distribute across instances)

## Current environment blocking factor
Product/Order services are currently failing to start because of MySQL auth:
- `Access denied for user 'root'@'localhost'`

Update `product-service/src/main/resources/application.yml` and `order-service/src/main/resources/application.yml` with correct MySQL username/password (and ensure databases exist).

Example (create DB):
```sql
CREATE DATABASE productdb;
CREATE DATABASE orderdb;
```

