
package com.nttdata.pedidos.exception;

import com.nttdata.pedidos.controller.OrderController;
import com.nttdata.pedidos.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("Deve retornar mensagem de erro personalizada para OrderNotFoundException")
    void shouldHandleOrderNotFoundException() throws Exception {
        when(orderService.getOrderById(99L)).thenThrow(new EntityNotFoundException("Pedido 99 não encontrado"));

        mockMvc.perform(get("/orders/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pedido 99 não encontrado"));
    }
}
