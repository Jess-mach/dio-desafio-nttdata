# Catálogo de Produtos

Este é um microserviço de catálogo de produtos desenvolvido com Java 21, Spring Boot 3, e banco de dados H2, como parte do bootcamp NTT Data.

## Funcionalidades
- Cadastro de produtos (nome, descrição, preço)
- Listagem paginada de produtos
- Obtenção de detalhes por ID
- Atualização e exclusão de produtos
- Autenticação simples via token fixo (UUID)
- Documentação interativa com Swagger

## Tecnologias
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- Springdoc OpenAPI (Swagger)
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

---

## Autenticação

Todas as chamadas aos endpoints protegidos devem conter o seguinte cabeçalho HTTP:

```
Authorization: Bearer 9177ed1d-81bf-480f-81ee-1c038b9bec69
```

---

## Endpoints e exemplos com curl

### 🔹 Cadastrar um produto

```bash
curl -X POST http://localhost:8100/produtos \
-H "Content-Type: application/json" \
-H "Authorization: Bearer 9177ed1d-81bf-480f-81ee-1c038b9bec69" \
-d '{
  "nome": "Notebook",
  "descricao": "Ultrabook leve e rápido",
  "preco": 4500.00
}'
```

---

### 🔹 Listar produtos (com paginação)

```bash
curl -X GET "http://localhost:8100/produtos?page=0&size=10" \
-H "Authorization: Bearer 9177ed1d-81bf-480f-81ee-1c038b9bec69"
```

---

### 🔹 Buscar produto por ID

```bash
curl -X GET http://localhost:8100/produtos/1 \
-H "Authorization: Bearer 9177ed1d-81bf-480f-81ee-1c038b9bec69"
```

---

### 🔹 Atualizar produto por ID

```bash
curl -X PUT http://localhost:8100/produtos/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer 9177ed1d-81bf-480f-81ee-1c038b9bec69" \
-d '{
  "nome": "Notebook Gamer",
  "descricao": "Com placa RTX 4060 e 32GB RAM",
  "preco": 6500.00
}'
```

---

### 🔹 Excluir produto por ID

```bash
curl -X DELETE http://localhost:8100/produtos/1 \
-H "Authorization: Bearer 9177ed1d-81bf-480f-81ee-1c038b9bec69"
```

---

## Swagger

Acesse a documentação interativa gerada automaticamente via Springdoc:

```
http://localhost:8100/swagger-ui.html
```

---

## H2 Console (banco em memória)

Acesse o console do banco de dados em:

```
http://localhost:8100/h2-console
```

- JDBC URL: `jdbc:h2:mem:catalogo`
- Usuário: `sa`
- Senha: *(deixe em branco)*

---

## Observações
- O token fixo pode ser alterado diretamente na classe `TokenAuthFilter.java`.
- O projeto é voltado para fins educacionais e não deve ser usado em produção sem ajustes de segurança adicionais.

---

## Autor

Projeto desenvolvido como parte do Bootcamp NTT Data na plataforma DIO.
