
# 🛒 Order Simulator Microservice

This microservice (`simulador-pedidos`) is part of a microservices architecture and is responsible for simulating orders based on a list of product IDs. It integrates with:

- 📦 Product Catalog Service (`catalogo-produtos`)
- 🛡️ API Gateway (`spring-cloud-gateway`)
- 🔎 Eureka Server (Service Discovery)

---

## 📌 Features

- Order creation with real-time product data
- Discovery via Eureka
- Swagger documentation enabled
- Feign-based communication with other services
- Token-based authentication via Gateway
- Fully compatible with Spring Boot 3.2.4 and Java 21

---

## ⚙️ Microservice Startup Order

To ensure proper registration and routing, follow this order:

1. **Eureka Server** (`http://localhost:8761`)
2. **Product Catalog** (`catalogo-produtos`)
3. **Order Simulator** (`simulador-pedidos`)
4. **API Gateway**

---

## 🔍 Eureka Health Check

After startup, confirm registration via browser or `curl`:

```bash
curl http://localhost:8761/eureka/apps
```

You should see: `catalogo-produtos`, `simulador-pedidos`, and `api-gateway`

---

## 🚀 API Gateway Proxy Access

Assuming your **API Gateway** runs on port **8080**:

### ✅ Create Order via Gateway

```bash
curl -X POST http://localhost:8080/pedidos/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer meu-token-secreto" \
  -d '{
        "productIds": [1, 2]
      }'
```

**Response:**

```json
{
  "id": "uuid",
  "products": [
    { "id": 1, "name": "Product A", "description": "desc", "price": 10.0 },
    { "id": 2, "name": "Product B", "description": "desc", "price": 20.0 }
  ],
  "totalAmount": 30.0
}
```

---

## 🧪 Test Without Gateway

To bypass the gateway for local testing:

```bash
curl -X POST http://localhost:8200/orders \
  -H "Content-Type: application/json" \
  -d '{
        "productIds": [1, 2]
      }'
```

---

## 🔐 Authentication Setup

If you have a custom `TokenAuthFilter` in the Gateway, use:

```http
Authorization: Bearer meu-token-secreto
```

This token must match what the Gateway is expecting.

---

## 📘 Swagger Documentation

Available at:
```
http://localhost:8200/swagger-ui.html
```

---

## ✅ Run Tests

```bash
mvn test
```

Includes:
- Unit tests: `OrderServiceTest`
- Integration tests: `OrderControllerIntegrationTest` (MockMvc)

---

## 📁 Directory Highlights

```
src/
├── controller/         --> REST controller
├── service/            --> Business logic
├── dto/                --> Request and response models
├── client/             --> Feign client to product service
├── model/              --> Domain models (Order)
├── test/               --> Unit and integration tests
```

---

## 💡 Tips

- Always ensure Eureka is up before starting dependent services.
- If using Docker, configure correct hostnames instead of `localhost`.

---

## 📬 Need help?

Feel free to reach out or inspect the Swagger docs to explore all available endpoints.
