# FSAD - API Gateway & Load Balancing (Spring Boot Microservices + Spring Cloud Gateway)

## What’s included
- **Eureka Server** (service registry)
- **API Gateway** (Spring Cloud Gateway) with dynamic routing via `lb://SERVICE_NAME`
- **Product Service** (CRUD, JPA + MySQL, Eureka client). Run multiple instances for load-balancing.
- **Order Service** (create order, JPA + MySQL). Fetches product details via **service discovery** (load-balanced WebClient).

## Endpoints (through Gateway)
- `GET    /products` (list)
- `GET    /products/{id}`
- `POST   /products`
- `PUT    /products/{id}`
- `DELETE /products/{id}`

- `POST   /orders`
- `GET    /orders`
- `GET    /orders/{id}`

All requests go through:
- **API Gateway**: `http://localhost:8080`

## Run locally (basic)
### 1) Prerequisites
- Java 17
- Maven
- MySQL running

Create databases:
```sql
CREATE DATABASE productdb;
CREATE DATABASE orderdb;
```
Adjust usernames/passwords in each `application.yml` if needed (currently `root/root`).

### 2) Start services (in separate terminals)
1. Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
- URL: `http://localhost:8761`

2. Product Service instance #1
```bash
cd product-service
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

3. Product Service instance #2 (for load balancing demo)
```bash
cd product-service
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083
```

> Same service name `PRODUCT-SERVICE`, different ports => Eureka has multiple instances.

4. Order Service
```bash
cd order-service
mvn spring-boot:run
```
- default port `8082`

5. API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```
- URL: `http://localhost:8080`

## How load balancing works
- Gateway routes `/products/**` to `lb://PRODUCT-SERVICE`
- Eureka exposes multiple Product instances
- Spring Cloud LoadBalancer chooses an instance per request

## Notes
- Ordering flow uses discovery + load-balanced `WebClient` to call `PRODUCT-SERVICE` without hard-coded URLs.
- Gateway uses discovery locator enabled.


