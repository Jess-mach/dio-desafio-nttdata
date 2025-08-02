package com.nttdata.pedidos.service;

import com.nttdata.pedidos.client.ProductClient;
import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.ProductResponse;
import com.nttdata.pedidos.dto.OrderResponse;
import com.nttdata.pedidos.model.OrderEntity;
import com.nttdata.pedidos.repository.OrderRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    public OrderService(ProductClient productClient, OrderRepository orderRepository) {
        this.productClient = productClient;
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(OrderRequest request) {
        List<OrderEntity.ProductInfo> productInfos = request.productIds().stream()
                .map(id -> {
                    ProductResponse product = findProductById(id);
                    OrderEntity.ProductInfo info = new OrderEntity.ProductInfo();
                    info.setId(product.id());
                    info.setName(product.name());
                    info.setPrice(product.price());
                    return info;
                })
                .collect(Collectors.toList());

        OrderEntity entity = new OrderEntity();
        entity.setCustomerName(request.customerName());
        entity.setCustomerEmail(request.customerEmail());
        entity.setProducts(productInfos);

        OrderEntity saved = orderRepository.save(entity);

        return mapToResponse(saved);
    }

    private ProductResponse findProductById(Long id) {
        try {
            return productClient.findById(id);
        } catch (FeignException e) {
            throw new EntityNotFoundException("Produto não encontrado com ID: " + id);
        }
    }

    public OrderResponse getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));

        return mapToResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrder(Long id, OrderRequest request) {
        OrderEntity existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        List<OrderEntity.ProductInfo> updatedProducts = request.productIds().stream()
                .map(productId -> {
                    ProductResponse product = findProductById(productId);
                    OrderEntity.ProductInfo info = new OrderEntity.ProductInfo();
                    info.setId(product.id());
                    info.setName(product.name());
                    info.setPrice(product.price());
                    return info;
                }).collect(Collectors.toList());

        existingOrder.setCustomerName(request.customerName());
        existingOrder.setCustomerEmail(request.customerEmail());
        existingOrder.setProducts(updatedProducts);

        OrderEntity saved = orderRepository.save(existingOrder);
        return mapToResponse(saved);
    }

    private OrderResponse mapToResponse(OrderEntity order) {
        List<ProductResponse> productDTOs = order.getProducts().stream()
                .map(p -> {
                    ProductResponse dto =
                            ProductResponse.builder()
                                    .id(p.getId())
                                    .name(p.getName())
                                    .price(p.getPrice())
                                    .build();

                    return dto;
                }).toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                productDTOs
        );

    }

}
