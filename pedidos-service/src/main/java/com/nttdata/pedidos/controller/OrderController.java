package com.nttdata.pedidos.controller;

import com.nttdata.pedidos.dto.OrderRequest;
import com.nttdata.pedidos.dto.OrderResponse;
import com.nttdata.pedidos.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Orders", description = "API for order management")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.created(URI.create("/orders/" + response.id()))
                .body(response);
    }

    @Operation(summary = "Get order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "List all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order list returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Update an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }
}
