
# 🧾 Projeto - Microserviço de Pedidos

Este projeto representa o microserviço de pedidos de um sistema baseado em arquitetura de microsserviços. Ele realiza operações de criação, consulta e comunicação com o microserviço de produtos via Feign.

---

## 📁 Estrutura do Projeto

```
src/
└── test/
    └── java/
        └── com/nttdata/orders/
            ├── controller/           # Testes com MockMvc para OrderController
            ├── service/              # Testes unitários de OrderService
            ├── dto/                  # Testes de OrderRequest e OrderResponse
            ├── client/               # Teste para resposta do ProductClient
            └── exception/            # Testes para exceções e handlers globais
```

---

## ▶️ Como Executar os Testes

```bash
mvn clean verify
```

> Isso executa todos os testes e gera o relatório de cobertura JaCoCo automaticamente.

---

## 📊 Como Ver o Relatório de Cobertura de Testes (JaCoCo)

Após executar os testes, abra o seguinte arquivo no navegador:

```
target/site/jacoco/index.html
```

> O relatório mostra a cobertura de linha, método e classe de todo o projeto.

---

## 🧪 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.4
- JUnit 5
- Mockito
- MockMvc
- JaCoCo
- Maven

---

## 📝 Observações

- Os testes não utilizam Testcontainers.
- Todos os métodos de teste estão anotados com `@DisplayName` para melhor legibilidade no relatório.
- O projeto está preparado para integração com o microserviço de produtos via Feign Client.
