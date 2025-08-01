package com.nttdata.pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.pedidos.client.ProductClient;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductClient productClient;

    @BeforeEach
    void setup() {
        when(productClient.getProductById(1L)).thenReturn(
                new ProductResponse(1L, "Test Product", "Desc", new BigDecimal("15.00")));
    }

    @Test
    void shouldReturn200WhenCreatingOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OrderRequest request = new OrderRequest(List.of(1L));
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
