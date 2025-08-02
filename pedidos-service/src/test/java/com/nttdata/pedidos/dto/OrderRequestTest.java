
package com.nttdata.pedidos.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderRequestTest {

    @Test
    @DisplayName("Deve criar OrderRequest corretamente")
    void shouldCreateOrderRequestCorrectly() {
        OrderRequest request = new OrderRequest(1L, 5);

        assertEquals(1L, request.getProductId());
        assertEquals(5, request.getQuantity());
    }
}
