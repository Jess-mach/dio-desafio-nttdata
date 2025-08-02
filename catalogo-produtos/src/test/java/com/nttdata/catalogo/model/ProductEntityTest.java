package com.nttdata.catalogo.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductEntityTest {

    @Test
    @DisplayName("Deve construir ProductEntity usando construtor com todos os argumentos")
    void testAllArgsConstructorAndGetters() {
        Long expectedId = 42L;
        String expectedName = "Caneta";
        String expectedDesc = "Caneta esferográfica azul";
        BigDecimal expectedPrice = new BigDecimal("3.50");

        ProductEntity entity = new ProductEntity(expectedId, expectedName, expectedDesc, expectedPrice);

        assertThat(entity.getId()).isEqualTo(expectedId);
        assertThat(entity.getName()).isEqualTo(expectedName);
        assertThat(entity.getDescription()).isEqualTo(expectedDesc);
        assertThat(entity.getPrice()).isEqualByComparingTo(expectedPrice);
    }

    @Test
    @DisplayName("Deve construir ProductEntity usando construtor sem argumentos e configurar campos via setters")
    void testNoArgsConstructorAndSetters() {
        ProductEntity entity = new ProductEntity();
        entity.setId(7L);
        entity.setName("Caderno");
        entity.setDescription("Caderno universitário 100 folhas");
        entity.setPrice(new BigDecimal("12.99"));

        assertThat(entity.getId()).isEqualTo(7L);
        assertThat(entity.getName()).isEqualTo("Caderno");
        assertThat(entity.getDescription()).isEqualTo("Caderno universitário 100 folhas");
        assertThat(entity.getPrice()).isEqualByComparingTo("12.99");
    }
}
