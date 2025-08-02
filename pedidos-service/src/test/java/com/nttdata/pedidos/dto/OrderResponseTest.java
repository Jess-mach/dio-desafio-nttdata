package com.nttdata.pedidos.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Test
    @DisplayName("Record OrderResponse deve armazenar e expor corretamente os valores")
    void testGetters() {
        ProductResponse p = ProductResponse.builder()
                .id(5L)
                .name("Produto X")
                .description("Desc")
                .price(BigDecimal.valueOf(99.99))
                .build();
        List<ProductResponse> products = List.of(p);

        OrderResponse resp = new OrderResponse(123L, "Bob", "bob@example.com", products);

        assertEquals(123, resp.id());
        assertEquals("Bob", resp.customerName());
        assertEquals("bob@example.com", resp.customerEmail());
        assertEquals(products, resp.products());
    }
}
