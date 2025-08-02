
package com.nttdata.orders.service;

import com.nttdata.orders.exception.OrderNotFoundException;
import com.nttdata.orders.model.Order;
import com.nttdata.orders.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Deve salvar um novo pedido com sucesso")
    void shouldSaveOrderSuccessfully() {
        Order order = new Order(null, 1L, 2);
        Order saved = new Order(1L, 1L, 2);

        when(orderRepository.save(order)).thenReturn(saved);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(2, result.getQuantity());
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("Deve retornar um pedido pelo ID")
    void shouldFindOrderById() {
        Order order = new Order(1L, 1L, 2);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o pedido não for encontrado")
    void shouldThrowWhenOrderNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(99L));
    }
}
