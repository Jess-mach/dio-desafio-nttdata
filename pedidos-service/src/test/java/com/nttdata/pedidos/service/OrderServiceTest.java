
package com.nttdata.pedidos.service;


import com.nttdata.pedidos.client.ProductClient;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.ProductResponse;
import com.nttdata.pedidos.dto.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
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

    @Test
    void testCreateOrderSuccessfully() {
        ProductClient productClient = mock(ProductClient.class);
        OrderService service = new OrderService(productClient);

        when(productClient.getProductById(1L)).thenReturn(
                new ProductResponse(1L, "Product A", "Desc", new BigDecimal("10.00")));
        when(productClient.getProductById(2L)).thenReturn(
                new ProductResponse(2L, "Product B", "Desc", new BigDecimal("20.00")));

        OrderRequest request = new OrderRequest(List.of(1L, 2L));
        OrderResponse order = service.createOrder(request);

        assertNotNull(order);
        assertEquals(2, order.products().size());
        assertEquals(new BigDecimal("30.00"), order.totalAmount());
    }
}
