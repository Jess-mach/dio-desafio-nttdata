package com.nttdata.pedidos.service;

import com.nttdata.pedidos.client.ProductClient;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.ProductResponse;
import com.nttdata.pedidos.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductClient productClient;

    public OrderService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public Order createOrder(OrderRequest request) {
        List<ProductResponse> products = request.productIds().stream()
                .map(productClient::getProductById)
                .collect(Collectors.toList());

        BigDecimal total = products.stream()
                .map(ProductResponse::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Order(UUID.randomUUID(), products, total);
    }
}
