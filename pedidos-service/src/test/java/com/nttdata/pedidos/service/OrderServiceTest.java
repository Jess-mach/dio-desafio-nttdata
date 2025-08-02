// src/test/java/com/nttdata/pedidos/service/OrderServiceTest.java
package com.nttdata.pedidos.service;

import com.nttdata.pedidos.client.ProductClient;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.OrderResponse;
import com.nttdata.pedidos.dto.ProductResponse;
import com.nttdata.pedidos.model.OrderEntity;
import com.nttdata.pedidos.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private ProductClient productClient;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Criar pedido - sucesso")
    void testCreateOrder() {
        OrderRequest request = new OrderRequest("John", "john@example.com", List.of(1L, 2L));

        ProductResponse p1 = ProductResponse.builder()
                .id(1L)
                .name("P1")
                .description("Desc1")
                .price(BigDecimal.valueOf(10))
                .build();
        ProductResponse p2 = ProductResponse.builder()
                .id(2L)
                .name("P2")
                .description("Desc2")
                .price(BigDecimal.valueOf(20))
                .build();

        when(productClient.findById(1L)).thenReturn(p1);
        when(productClient.findById(2L)).thenReturn(p2);

        OrderEntity toSave = new OrderEntity();
        toSave.setCustomerName("John");
        toSave.setCustomerEmail("john@example.com");
        toSave.setProducts(List.of(
                new OrderEntity.ProductInfo(1L, "P1", BigDecimal.valueOf(10)),
                new OrderEntity.ProductInfo(2L, "P2", BigDecimal.valueOf(20))
        ));

        OrderEntity saved = new OrderEntity();
        saved.setId(100L);
        saved.setCustomerName("John");
        saved.setCustomerEmail("john@example.com");
        saved.setProducts(toSave.getProducts());

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(saved);

        OrderResponse response = orderService.createOrder(request);

        assertNotNull(response.id());
        assertEquals("John", response.customerName());
        assertEquals(2, response.products().size());
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    @DisplayName("Buscar pedido por ID - sucesso")
    void testGetOrderById() {
        OrderEntity entity = new OrderEntity();
        entity.setId(1L);
        entity.setCustomerName("Jane");
        entity.setCustomerEmail("jane@example.com");
        entity.setProducts(List.of());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(entity));

        OrderResponse response = orderService.getOrderById(1L);

        assertEquals("Jane", response.customerName());
        assertTrue(response.products().isEmpty());
    }

    @Test
    @DisplayName("Buscar pedido por ID - não encontrado")
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    @DisplayName("Listar todos os pedidos")
    void testGetAllOrders() {
        OrderEntity e1 = new OrderEntity();
        e1.setId(1L);
        e1.setCustomerName("A");
        e1.setCustomerEmail("a@example.com");
        e1.setProducts(List.of());

        OrderEntity e2 = new OrderEntity();
        e2.setId(2L);
        e2.setCustomerName("B");
        e2.setCustomerEmail("b@example.com");
        e2.setProducts(List.of());

        when(orderRepository.findAll()).thenReturn(List.of(e1, e2));

        var responses = orderService.getAllOrders();

        assertEquals(2, responses.size());
    }

    @Test
    @DisplayName("Atualizar pedido - sucesso")
    void testUpdateOrder() {
        OrderRequest request = new OrderRequest("Joe", "joe@example.com", List.of(1L));

        OrderEntity existing = new OrderEntity();
        existing.setId(1L);
        existing.setCustomerName("Old");
        existing.setCustomerEmail("old@example.com");
        existing.setProducts(List.of());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existing));

        ProductResponse p = ProductResponse.builder()
                .id(1L)
                .name("P")
                .description("Desc")
                .price(BigDecimal.valueOf(5))
                .build();
        when(productClient.findById(1L)).thenReturn(p);

        OrderEntity saved = new OrderEntity();
        saved.setId(1L);
        saved.setCustomerName("Joe");
        saved.setCustomerEmail("joe@example.com");
        saved.setProducts(List.of(new OrderEntity.ProductInfo(1L, "P", BigDecimal.valueOf(5))));

        when(orderRepository.save(existing)).thenReturn(saved);

        OrderResponse resp = orderService.updateOrder(1L, request);

        assertEquals("Joe", resp.customerName());
        assertEquals(1, resp.products().size());
        verify(orderRepository).save(existing);
    }
}
