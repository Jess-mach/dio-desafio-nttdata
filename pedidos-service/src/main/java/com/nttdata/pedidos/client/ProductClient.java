package com.nttdata.pedidos.client;

import com.nttdata.pedidos.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogo-produtos", path = "/products")
public interface ProductClient {

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable Long id);
}
