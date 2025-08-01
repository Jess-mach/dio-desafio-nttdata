# Order Simulator Microservice

This microservice simulates the creation of orders by retrieving product information from the Product Catalog microservice.

## Features

- Create order based on a list of product IDs
- Integration with `catalogo-produtos` via Feign
- Registered on Eureka Discovery Server
- Secured via API Gateway (token-based)
- Swagger documentation enabled

## Requirements

- Java 17+
- Maven
- Eureka Discovery Server running
- API Gateway configured and running

## Run Locally

```bash
mvn spring-boot:run
```

Access:
- Swagger: [http://localhost:8200/swagger-ui.html](http://localhost:8200/swagger-ui.html)

## API Endpoints

### `POST /orders`

Creates a new order.

**Request:**
```json
{
  "productIds": [1, 2]
}
```

**Response:**
```json
{
  "id": "uuid",
  "products": [
    { "id": 1, "name": "P1", "description": "desc", "price": 10.00 },
    { "id": 2, "name": "P2", "description": "desc", "price": 20.00 }
  ],
  "totalAmount": 30.00
}
```

## Testing

```bash
mvn test
```

Includes:
- Unit tests for service logic
- Integration test for controller
