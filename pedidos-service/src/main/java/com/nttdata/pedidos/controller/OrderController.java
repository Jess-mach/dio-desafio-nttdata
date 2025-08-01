package com.nttdata.pedidos.controller;

import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.model.Order;
import com.nttdata.pedidos.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(summary = "Create an order based on a list of product IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }
}
