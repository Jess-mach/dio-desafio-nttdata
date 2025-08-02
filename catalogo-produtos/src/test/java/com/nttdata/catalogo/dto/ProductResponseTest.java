package com.nttdata.catalogo.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseTest {

    @Test
    @DisplayName("Deve retornar valores corretos dos getters do record ProductResponse")
    void testRecordAccessors() {
        Long id = 99L;
        String name = "Teclado";
        String desc = "Teclado mecânico RGB";
        BigDecimal price = new BigDecimal("249.90");

        ProductResponse response = new ProductResponse(id, name, desc, price);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.name()).isEqualTo(name);
        assertThat(response.description()).isEqualTo(desc);
        assertThat(response.price()).isEqualByComparingTo(price);
    }
}
