# Microserviço de Pedidos

Este microserviço gerencia pedidos contendo produtos, com autenticação, persistência, DTOs e testes automatizados.

## Tecnologias
- Java 21 + Spring Boot 3
- Spring Data JPA + H2
- Spring Security
- Testes com JUnit + Mockito
- Docker + Docker Compose
- JaCoCo para cobertura de testes

## Como executar
```bash
./mvnw clean package
docker-compose up --build
```

A API estará disponível em: [http://localhost:8200/pedidos](http://localhost:8200/pedidos)
