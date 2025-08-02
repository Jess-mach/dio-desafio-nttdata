
package com.nttdata.orders.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderNotFoundExceptionTest {

    @Test
    @DisplayName("Deve instanciar OrderNotFoundException com mensagem")
    void shouldInstantiateExceptionWithMessage() {
        String message = "Pedido não encontrado";
        OrderNotFoundException exception = new OrderNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }
}
