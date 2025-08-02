package com.nttdata.pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.pedidos.client.ProductClient;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.ProductResponse;
import com.nttdata.pedidos.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Criar novo pedido - sucesso")
    void shouldCreateNewOrder() throws Exception {
        ProductResponse product = ProductResponse.builder()
                .id(1L)
                .name("Product 1")
                .description("Descrição do produto")
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productClient.getProductById(anyLong())).thenReturn(product);

        OrderRequest request = new OrderRequest(
                "John Doe",
                "john@example.com",
                List.of(1L)
        );

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.customerEmail").value("john@example.com"))
                .andExpect(jsonPath("$.products[0].id").value(1));
    }

    @Test
    @DisplayName("Buscar pedido por ID - sucesso")
    void shouldGetOrderById() throws Exception {
        // Primeiro criamos um pedido
        ProductResponse product = ProductResponse.builder()
                .id(2L)
                .name("Product 2")
                .description("Segundo produto")
                .price(BigDecimal.valueOf(20.0))
                .build();
        when(productClient.getProductById(anyLong())).thenReturn(product);

        OrderRequest createReq = new OrderRequest(
                "Jane Doe",
                "jane@example.com",
                List.of(2L)
        );
        String body = objectMapper.writeValueAsString(createReq);

        String json = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(json).get("id").asText();

        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.customerName").value("Jane Doe"));
    }

    @Test
    @DisplayName("Listar todos os pedidos - sucesso")
    void shouldGetAllOrders() throws Exception {
        ProductResponse product = ProductResponse.builder()
                .id(3L)
                .name("Product 3")
                .description("Terceiro produto")
                .price(BigDecimal.valueOf(30.0))
                .build();
        when(productClient.getProductById(anyLong())).thenReturn(product);

        OrderRequest req1 = new OrderRequest("A", "a@example.com", List.of(3L));
        OrderRequest req2 = new OrderRequest("B", "b@example.com", List.of(3L));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req2)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Atualizar pedido existente - sucesso")
    void shouldUpdateOrder() throws Exception {
        ProductResponse original = ProductResponse.builder()
                .id(4L)
                .name("Original")
                .description("Produto original")
                .price(BigDecimal.valueOf(40.0))
                .build();
        when(productClient.getProductById(anyLong())).thenReturn(original);

        OrderRequest createReq = new OrderRequest("C", "c@example.com", List.of(4L));
        String json = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(json).get("id").asText();

        ProductResponse updatedProd = ProductResponse.builder()
                .id(5L)
                .name("Updated")
                .description("Produto atualizado")
                .price(BigDecimal.valueOf(50.0))
                .build();
        when(productClient.getProductById(5L)).thenReturn(updatedProd);

        OrderRequest updateReq = new OrderRequest("C Updated", "c_updated@example.com", List.of(5L));

        mockMvc.perform(put("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("C Updated"))
                .andExpect(jsonPath("$.products[0].id").value(5));
    }
}
