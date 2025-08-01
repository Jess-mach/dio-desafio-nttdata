# Catálogo de Produtos

Este é um microserviço de catálogo de produtos desenvolvido com Java 21, Spring Boot 3, e banco de dados H2, como parte do bootcamp NTT Data.

## Funcionalidades
- Cadastro de produtos (nome, descrição, preço)
- Listagem paginada de produtos
- Obtenção de detalhes por ID
- Autenticação simples via token fixo

## Tecnologias
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- H2 Database
- Maven
- Docker

## Como executar

### Via Maven
```bash
mvn spring-boot:run
```

### Via Docker
```bash
docker-compose up --build
```

## Autenticação
Use um token fixo no header:

```
Authorization: Bearer secret-token
```

## Endpoints principais
- `POST /produtos`
- `GET /produtos`
- `GET /produtos/{id}`
- `PUT /produtos/{id}`
- `DELETE /produtos/{id}`
