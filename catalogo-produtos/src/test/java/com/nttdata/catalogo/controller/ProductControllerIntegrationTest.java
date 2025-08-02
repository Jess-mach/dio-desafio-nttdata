package com.nttdata.catalogo.controller;

import com.nttdata.catalogo.model.ProductEntity;
import com.nttdata.catalogo.repository.ProductRepository;
import com.nttdata.catalogo.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /products - when no products, return empty list")
    void whenNoProducts_thenGetAllReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @DisplayName("GET /products - return list of products")
    void whenProductsExist_thenGetAllReturnsList() throws Exception {
        ProductEntity p1 = new ProductEntity(null,"Notebook", "Notebook de alta performance", new BigDecimal("4500.00"));
        ProductEntity p2 = new ProductEntity(null,"Mouse", "Mouse sem fio", new BigDecimal("150.00"));
        productRepository.saveAll(List.of(p1, p2));

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content.[0].name").value("Notebook"))
                .andExpect(jsonPath("$.content.[1].name").value("Mouse"));
    }

    @Test
    @DisplayName("GET /products/{id} - when product exists, return product")
    void whenProductExists_thenGetByIdReturnsProduct() throws Exception {
        ProductEntity p = new ProductEntity(null,"Teclado", "Teclado mecânico", new BigDecimal("350.00"));
        p = productRepository.save(p);

        mockMvc.perform(get("/products/{id}", p.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(p.getId()))
                .andExpect(jsonPath("$.name").value("Teclado"))
                .andExpect(jsonPath("$.price").value(350.00));
    }

    @Test
    @DisplayName("GET /products/{id} - when product does not exist, return 404")
    void whenProductNotFound_thenGetByIdReturns404() throws Exception {
        mockMvc.perform(get("/products/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /products - when request is valid, create product")
    void whenValidInput_thenCreateProduct() throws Exception {
        ProductRequest dto = new ProductRequest();
        dto.setName("Caneta");
        dto.setDescription("Caneta tinteiro");
        dto.setPrice(new BigDecimal("25.00"));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Caneta"));

        assertThat(productRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("PUT /products/{id} - when product exists, update and return 200")
    void whenValidUpdate_thenUpdateProduct() throws Exception {
        ProductEntity p = new ProductEntity(null,"Caderno", "Caderno universitário", new BigDecimal("20.00"));
        p = productRepository.save(p);

        ProductRequest dto = new ProductRequest();
        dto.setName("Caderno Atualizado");
        dto.setDescription("Caderno A5");
        dto.setPrice(new BigDecimal("22.00"));

        mockMvc.perform(put("/products/{id}", p.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(p.getId()))
                .andExpect(jsonPath("$.name").value("Caderno Atualizado"));

        ProductEntity updated = productRepository.findById(p.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("Caderno Atualizado");
    }

    @Test
    @DisplayName("DELETE /products/{id} - when product exists, delete and return 204")
    void whenValidDelete_thenDeleteProduct() throws Exception {
        ProductEntity p = new ProductEntity(null,"Lapiseira", "Lapiseira 0.5mm", new BigDecimal("18.00"));
        p = productRepository.save(p);

        mockMvc.perform(delete("/products/{id}", p.getId()))
                .andExpect(status().isNoContent());

        assertThat(productRepository.existsById(p.getId())).isFalse();
    }
}
