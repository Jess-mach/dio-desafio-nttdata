
package com.nttdata.pedidos.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderNotFoundExceptionTest {

    @Test
    @DisplayName("Deve instanciar OrderNotFoundException com mensagem")
    void shouldInstantiateExceptionWithMessage() {
        String message = "Pedido não encontrado";
        OrderNotFoundException exception = new OrderNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }
}
