
package com.nttdata.orders.client;

import com.nttdata.orders.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductClientTest {

    @Test
    @DisplayName("Simulação de instanciamento de ProductResponse recebido do ProductClient")
    void shouldInstantiateProductResponse() {
        ProductResponse response = new ProductResponse(1L, "Produto Teste", "Descrição", 10.0);

        assertEquals(1L, response.getId());
        assertEquals("Produto Teste", response.getName());
        assertEquals("Descrição", response.getDescription());
        assertEquals(10.0, response.getPrice());
    }
}
