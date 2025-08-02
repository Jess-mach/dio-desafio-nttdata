# 🧪 Projeto de Microserviços com Spring Boot, Spring Cloud e Docker

Este repositório contém quatro microsserviços desenvolvidos no Bootcamp NTT Data da DIO:

* 📦 **catalogo-produtos**: gerenciamento de produtos
* 🛒 **servico-pedidos**: processamento de pedidos
* 📡 **eureka-server**: service discovery (Eureka)
* 🚪 **api-gateway**: roteamento e autenticação de requisições

---

## 📋 Tecnologias Utilizadas

* Java 21
* Spring Boot 3.2.x
* Spring Cloud (Eureka, Gateway)
* Maven
* Docker & Docker Compose
* H2 (ambiente de desenvolvimento)
* Swagger/OpenAPI
* JUnit & Mockito
* Cobertura de testes com JaCoCo

---

## 🚀 Execução Local (sem Docker)

### Pré-requisitos

* JDK 21 instalado
* Maven (opcional: use `./mvnw` incluso)
* Porta `8761`, `8100`, `8200` e `8080` livres

### Passos

1. **Gerar artefatos**

   ```bash
   cd catalogo-produtos && ./mvnw clean package -DskipTests
   cd ../servico-pedidos && ./mvnw clean package -DskipTests
   cd ../eureka-server && ./mvnw clean package -DskipTests
   cd ../api-gateway && ./mvnw clean package -DskipTests
   cd ..
   ```

2. **Executar cada microsserviço** (em terminais separados):

   ```bash
   # Service Discovery
   cd eureka-server && java -jar target/eureka-server.jar

   # Catálogo de Produtos
   cd ../catalogo-produtos && java -jar target/catalogo-produtos.jar

   # Serviço de Pedidos
   cd ../servico-pedidos && java -jar target/servico-pedidos.jar

   # API Gateway
   cd ../api-gateway && java -jar target/api-gateway.jar
   ```

3. **Acessar endpoints**

   * Eureka Server: `http://localhost:8761`
   * Catalogo de Produtos: `http://localhost:8100` (também via Gateway em `http://localhost:8080/products`)
   * Serviço de Pedidos: `http://localhost:8200` (via Gateway em `http://localhost:8080/orders`)
   * Gateway Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 🐳 Execução via Docker Compose

### Pré-requisitos

* Docker e Docker Compose instalados
* Portas `8761:8761`, `8100:8100`, `8200:8200`, `8080:8080` livres

### Passos

1. **Gerar JARs** (igual à seção acima):

   ```bash
   ./mvnw clean package -DskipTests
   ```
2. **Subir containers**

   ```bash
   docker-compose up --build -d
   ```
3. **Verificar**

   * `docker ps` para containers em execução
   * Acesse `http://localhost:8761` para conferir registro dos serviços

---

## 🔍 Testes de API via `curl`

> Se você possui autenticação JWT, primeiro faça login e salve o token:

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"usuario","password":"senha"}' \
  | jq -r .access_token)
```

### 1. Catálogo de Produtos

* **Listar produtos**

  ```bash
  curl -X GET http://localhost:8080/products \
    -H "Authorization: Bearer $TOKEN"
  ```

* **Criar produto**

  ```bash
  curl -X POST http://localhost:8080/products \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
      "name": "Notebook Gamer",
      "description": "RTX 4060, i7, 16GB RAM",
      "price": 7500.00
    }'
  ```

* **Buscar por ID**

  ```bash
  curl -X GET http://localhost:8080/products/1 \
    -H "Authorization: Bearer $TOKEN"
  ```

* **Atualizar produto**

  ```bash
  curl -X PUT http://localhost:8080/products/1 \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
      "name": "Notebook Gamer Ultra",
      "description": "RTX 4070, i9, 32GB RAM",
      "price": 9500.00
    }'
  ```

* **Deletar produto**

  ```bash
  curl -X DELETE http://localhost:8080/products/1 \
    -H "Authorization: Bearer $TOKEN"
  ```

### 2. Serviço de Pedidos

* **Listar pedidos**

  ```bash
  curl -X GET http://localhost:8080/orders \
    -H "Authorization: Bearer $TOKEN"
  ```

* **Criar pedido**

  ```bash
  curl -X POST http://localhost:8080/orders \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
      "customerName": "João da Silva",
      "customerEmail": "joao@example.com",
      "productIds": [1, 2]
    }'
  ```

* **Buscar por ID**

  ```bash
  curl -X GET http://localhost:8080/orders/1 \
    -H "Authorization: Bearer $TOKEN"
  ```

* **Atualizar pedido**

  ```bash
  curl -X PUT http://localhost:8080/orders/1 \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
      "customerName": "João Atualizado",
      "customerEmail": "joao.new@example.com",
      "productIds": [2, 3]
    }'
  ```

* **Deletar pedido**

  ```bash
  curl -X DELETE http://localhost:8080/orders/1 \
    -H "Authorization: Bearer $TOKEN"
  ```

---

## 🐛 Logs e Debug

* Consulte logs de cada serviço para verificar portas e registros no Eureka.
* No Docker Compose, use `docker logs <container>`.

---

## 📬 Contato

*Desenvolvedor:* Jessica Machado Franco
*LinkedIn:* [https://www.linkedin.com/in/jessica-machado-franco-4aa149249](https://www.linkedin.com/in/jessica-machado-franco-4aa149249)
*Data:* Agosto/2025


### Adicionais:
Collections para o postman:
 Order Simulator Test Collection.postman_collection.json

Subir apenas o eureka e api gateway via docker compose: 
  ```bash
docker-compose -f docker-compose-eureka-gateway.yml up --build -d
  ```