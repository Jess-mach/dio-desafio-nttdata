
package com.nttdata.orders.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Test
    @DisplayName("Deve criar OrderResponse corretamente")
    void shouldCreateOrderResponseCorrectly() {
        OrderResponse response = new OrderResponse(1L, 2L, 5);

        assertEquals(1L, response.getId());
        assertEquals(2L, response.getProductId());
        assertEquals(5, response.getQuantity());
    }
}
