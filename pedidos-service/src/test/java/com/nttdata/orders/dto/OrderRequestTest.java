
package com.nttdata.orders.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestTest {

    @Test
    @DisplayName("Deve criar OrderRequest corretamente")
    void shouldCreateOrderRequestCorrectly() {
        OrderRequest request = new OrderRequest(1L, 5);

        assertEquals(1L, request.getProductId());
        assertEquals(5, request.getQuantity());
    }
}
