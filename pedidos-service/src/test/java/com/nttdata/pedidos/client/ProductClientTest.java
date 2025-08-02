package com.nttdata.pedidos.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ProductClientTest {

    @Test
    @DisplayName("ProductClient deve estar anotado com @FeignClient(name='catalogo-produtos', path='/produtos')")
    void testFeignClientAnnotation() {
        FeignClient ann = ProductClient.class.getAnnotation(FeignClient.class);
        assertNotNull(ann, "Esperava @FeignClient presente na interface");
        assertEquals("catalogo-produtos", ann.name());
        assertEquals("/produtos", ann.path());
    }

    @Test
    @DisplayName("Método getProductById deve ter @GetMapping com '/{id}'")
    void testFindByIdMapping() throws NoSuchMethodException {
        Method m = ProductClient.class.getMethod("findById", Long.class);
        GetMapping gm = m.getAnnotation(GetMapping.class);
        assertNotNull(gm, "Esperava @GetMapping no método");
        assertArrayEquals(new String[]{"/{id}"}, gm.value());
    }
}
