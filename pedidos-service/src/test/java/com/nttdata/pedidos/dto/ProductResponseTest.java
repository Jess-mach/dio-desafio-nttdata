package com.nttdata.pedidos.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseTest {

    @Test
    @DisplayName("Builder de ProductResponse deve criar instância com todos os campos corretamente")
    void testBuilder() {
        ProductResponse pr = ProductResponse.builder()
                .id(42L)
                .name("Produto Y")
                .description("Descrição Y")
                .price(BigDecimal.valueOf(123.45))
                .build();

        assertEquals(42L, pr.id());
        assertEquals("Produto Y", pr.name());
        assertEquals("Descrição Y", pr.description());
        assertEquals(BigDecimal.valueOf(123.45), pr.price());
    }
}
