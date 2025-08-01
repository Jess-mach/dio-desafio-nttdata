package com.nttdata.pedidos.service;

import com.nttdata.pedidos.client.ProductClient;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.ProductResponse;
import com.nttdata.pedidos.model.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Test
    void testCreateOrderSuccessfully() {
        ProductClient productClient = mock(ProductClient.class);
        OrderService service = new OrderService(productClient);

        when(productClient.getProductById(1L)).thenReturn(
                new ProductResponse(1L, "Product A", "Desc", new BigDecimal("10.00")));
        when(productClient.getProductById(2L)).thenReturn(
                new ProductResponse(2L, "Product B", "Desc", new BigDecimal("20.00")));

        OrderRequest request = new OrderRequest(List.of(1L, 2L));
        Order order = service.createOrder(request);

        assertNotNull(order);
        assertEquals(2, order.products().size());
        assertEquals(new BigDecimal("30.00"), order.totalAmount());
    }
}
