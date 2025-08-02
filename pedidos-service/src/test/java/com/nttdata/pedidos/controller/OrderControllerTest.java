
package com.nttdata.pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.OrderResponse;
import com.nttdata.pedidos.dto.ProductResponse;
import com.nttdata.pedidos.model.OrderEntity;
import com.nttdata.pedidos.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um pedido com sucesso via POST /orders")
    void shouldCreateOrder() throws Exception {
        OrderRequest request = new OrderRequest("Jessica", "email@email.com", List.of(1L, 2L));
        OrderResponse savedOrder = new OrderResponse(1L, "Jessica", "email@email.com",
                List.of(new ProductResponse(1L, "Produto 1", "", BigDecimal.ONE),
                        new ProductResponse(2L, "Produto 2", "", BigDecimal.TWO)));

        when(orderService.createOrder(any())).thenReturn(savedOrder);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.productId").value(1L))
            .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    @DisplayName("Deve retornar um pedido via GET /orders/{id}")
    void shouldGetOrderById() throws Exception {
        Order order = new Order(1L, 1L, 2);
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.productId").value(1L))
            .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    @DisplayName("Deve retornar 404 se o pedido não for encontrado")
    void shouldReturnNotFound() throws Exception {
        when(orderService.getOrderById(99L)).thenThrow(new OrderNotFoundException("Pedido não encontrado"));

        mockMvc.perform(get("/orders/99"))
            .andExpect(status().isNotFound());
    }
}
