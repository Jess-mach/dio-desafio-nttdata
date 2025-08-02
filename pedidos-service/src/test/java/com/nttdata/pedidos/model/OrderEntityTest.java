package com.nttdata.pedidos.model;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    @Test
    @DisplayName("OrderEntity deve ter @Entity e @Table(name = 'orders')")
    void testClassAnnotations() {
        assertTrue(OrderEntity.class.isAnnotationPresent(Entity.class));
        Table tbl = OrderEntity.class.getAnnotation(Table.class);
        assertNotNull(tbl);
        assertEquals("orders", tbl.name());
    }

    @Test
    @DisplayName("Campo 'products' deve ter @ElementCollection e @CollectionTable configurados corretamente")
    void testProductsFieldAnnotations() throws NoSuchFieldException {
        Field fld = OrderEntity.class.getDeclaredField("products");
        assertTrue(fld.isAnnotationPresent(ElementCollection.class));
        CollectionTable ct = fld.getAnnotation(CollectionTable.class);
        assertNotNull(ct);
        assertEquals("order_products", ct.name());
        JoinColumn[] jcs = ct.joinColumns();
        assertEquals(1, jcs.length);
        assertEquals("order_id", jcs[0].name());
    }

    @Test
    @DisplayName("ProductInfo interna deve ser @Embeddable")
    void testProductInfoAnnotation() {
        assertTrue(OrderEntity.ProductInfo.class.isAnnotationPresent(Embeddable.class));
    }

    @Test
    @DisplayName("Getters e setters de OrderEntity devem funcionar corretamente")
    void testGettersAndSetters() throws Exception {
        OrderEntity e = new OrderEntity();
        e.setId(7L);
        e.setCustomerName("Carol");
        e.setCustomerEmail("carol@example.com");

        OrderEntity.ProductInfo pi = new OrderEntity.ProductInfo(8L, "Z", BigDecimal.TEN);
        e.setProducts(List.of(pi));

        assertEquals(7L, e.getId());
        assertEquals("Carol", e.getCustomerName());
        assertEquals("carol@example.com", e.getCustomerEmail());
        assertEquals(1, e.getProducts().size());
        assertEquals(pi, e.getProducts().get(0));
    }

    @Test
    @DisplayName("Getters e setters de ProductInfo interna devem funcionar corretamente")
    void testProductInfoGettersAndSetters() {
        OrderEntity.ProductInfo pi = new OrderEntity.ProductInfo(9L, "W", BigDecimal.ONE);
        assertEquals(9L, pi.getId());
        assertEquals("W", pi.getName());
        assertEquals(BigDecimal.ONE, pi.getPrice());

        pi.setName("W2");
        assertEquals("W2", pi.getName());
    }
}
