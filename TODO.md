# TODO - API Gateway & Load Balancing (Spring Cloud Gateway + Eureka + LoadBalancer)

- [x] Create root multi-module Maven project (pom.xml) with modules:
  - eureka-server
  - api-gateway
  - product-service
  - order-service

- [ ] Add shared dependency management and versions (Spring Boot 3.x, Spring Cloud 202x) in root pom.
- [x] Implement Eureka Server module (standalone Spring Boot app).

- [x] Implement API Gateway module (Spring Cloud Gateway) with routes using dynamic `lb://SERVICE_NAME`.

- [x] Implement Product Service module:
  - JPA entity, repository, service, controller (CRUD)
  - Eureka client registration
  - Run multiple instances via different server.port (for demo)

- [x] Implement Order Service module:
  - Order entity + CRUD
  - Use Service Discovery (Eureka) + Spring Cloud LoadBalancer / WebClient to fetch product details from product-service without hard-coded URLs

- [ ] Add MySQL/PostgreSQL configuration for both services (profiles) + SQL schema/sample data.
- [ ] Add global exception handling + validation.
- [ ] Add Actuator to services.
- [ ] Add Resilience4j circuit breaker (optional if time) for downstream calls.
- [ ] Add Dockerfiles + docker-compose for local demo (eureka + gateway + two product instances + order).
- [ ] Add Postman collection (optional) + README with step-by-step setup and diagrams.
- [ ] Verify build: `mvn clean package` and basic run instructions.

