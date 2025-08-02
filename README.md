# 🧪 Projeto de Microserviços com Spring Boot, Spring Cloud e Docker

Este projeto foi desenvolvido como parte do desafio do bootcamp NTT Data da DIO. O sistema é composto por quatro microsserviços principais:

- 📦 `catalogo-produtos`: gerenciamento de produtos.
- 🛒 `servico-pedidos`: processamento de pedidos.
- 📡 `eureka-server`: service discovery.
- 🚪 `api-gateway`: roteamento e autenticação.

---

## 🐳 Como Executar via Docker Compose

### Pré-requisitos

- Docker e Docker Compose instalados

### Passos:

```bash
# 1. Gere os JARs de cada microserviço
cd catalogo-produtos && ./mvnw clean package -DskipTests
cd ../servico-pedidos && ./mvnw clean package -DskipTests
cd ../api-gateway && ./mvnw clean package -DskipTests
cd ../eureka-server && ./mvnw clean package -DskipTests

# 2. Volte à raiz e execute o docker-compose
cd ..
docker-compose up --build

Acesso aos serviços:
Serviço	URL
Eureka Server	http://localhost:8761
API Gateway	http://localhost:8080
Catálogo de Produtos	http://localhost:8080/products
Serviço de Pedidos	http://localhost:8080/orders

⚠️ Lembre-se de que as requisições passam pelo API Gateway.

🧪 Como Testar
Verificar o Eureka
Acesse http://localhost:8761 e confirme que todos os serviços estão registrados.

Testar produtos

curl http://localhost:8080/products
Testar pedidos


curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":2}'
🧾 Informações do Projeto
Desenvolvedor: Jessica Machada Franco

Bootcamp: NTT Data Java & Spring Boot - DIO

Data: Agosto de 2025

Tecnologias:

Java 21

Spring Boot 3.2.x

Spring Cloud (Eureka, Gateway)

Docker

Swagger/OpenAPI

Testes com JUnit e Mockito

Cobertura com JaCoCo

💡 Dicas
Use o Swagger para explorar os endpoints:

http://localhost:8080/swagger-ui.html

Verifique os logs para confirmar a comunicação entre serviços:

Eureka detectando os serviços

API Gateway roteando para os microserviços

# 🧪 Projeto de Microserviços com Spring Boot, Spring Cloud e Docker

Este projeto foi desenvolvido como parte do desafio do bootcamp **NTT Data** da **DIO**. O sistema é composto por quatro microsserviços principais:

- 📦 `catalogo-produtos`: gerenciamento de produtos.
- 🛒 `servico-pedidos`: processamento de pedidos.
- 📡 `eureka-server`: service discovery.
- 🚪 `api-gateway`: roteamento e autenticação de requisições.

...

## 📬 Contato

Caso precise entrar em contato para dúvidas ou demonstração:

Jessica Machado Frando - [https://www.linkedin.com/in/jessica-machado-franco-4aa149249/]


# ✅ Testes de API - Projeto de Microserviços (Produtos e Pedidos)

Este documento descreve como testar os microserviços utilizando `curl`, passando pelo API Gateway com autenticação JWT.

---

## 🧾 Pré-requisitos

- Docker e Docker Compose configurados.
- API Gateway rodando na porta `8080`.
- Token JWT válido.
- Microserviços de produtos (`/products`) e pedidos (`/orders`) registrados no Eureka e acessíveis via Gateway.

---

## 🔐 Autenticação

> Substitua `usuario` e `senha` por credenciais válidas, caso tenha um serviço de autenticação ativo.

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"usuario", "password":"senha"}'
```

A resposta conterá o `access_token`. Use-o nos comandos abaixo substituindo `<SEU_TOKEN>`.

---

## 🛒 Microserviço de Produtos

Base URL: `http://localhost:8080/products`

### ▶️ Criar Produto

```bash
curl -X POST http://localhost:8080/products \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook Gamer",
    "description": "RTX 4060, i7, 16GB RAM",
    "price": 7500.00
  }'
```

---

### 📃 Listar Produtos (com paginação)

```bash
curl -X GET "http://localhost:8080/products?page=0&size=10" \
  -H "Authorization: Bearer <SEU_TOKEN>"
```

---

### 🔎 Buscar Produto por ID

```bash
curl -X GET http://localhost:8080/products/1 \
  -H "Authorization: Bearer <SEU_TOKEN>"
```

---

### ✏️ Atualizar Produto

```bash
curl -X PUT http://localhost:8080/products/1 \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook Gamer Ultra",
    "description": "RTX 4070, i9, 32GB RAM",
    "price": 9500.00
  }'
```

---

### ❌ Deletar Produto

```bash
curl -X DELETE http://localhost:8080/products/1 \
  -H "Authorization: Bearer <SEU_TOKEN>"
```

---

## 📦 Microserviço de Pedidos

Base URL: `http://localhost:8080/orders`

### ▶️ Criar Pedido

```bash
curl -X POST http://localhost:8080/orders \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "João da Silva",
    "productIds": [1, 2]
  }'
```

---

### 📃 Listar Pedidos

```bash
curl -X GET http://localhost:8080/orders \
  -H "Authorization: Bearer <SEU_TOKEN>"
```

---

### 🔎 Buscar Pedido por ID

```bash
curl -X GET http://localhost:8080/orders/1 \
  -H "Authorization: Bearer <SEU_TOKEN>"
```

---

### ✏️ Atualizar Pedido

```bash
curl -X PUT http://localhost:8080/orders/1 \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "João Atualizado",
    "productIds": [2, 3]
  }'
```

---

### ❌ Deletar Pedido

```bash
curl -X DELETE http://localhost:8080/orders/1 \
  -H "Authorization: Bearer <SEU_TOKEN>"
```

---

## 🧪 Dica: Automatize com script

Você pode criar um arquivo `.sh` com todos esses comandos e executar os testes de forma automatizada.

---

## 🧩 Observações

- Todos os endpoints são acessados via **API Gateway**.
- O token JWT precisa estar presente em **todas** as requisições (exceto login).
- Se um produto não existir, o pedido será rejeitado.

---
