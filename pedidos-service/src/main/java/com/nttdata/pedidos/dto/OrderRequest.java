package com.nttdata.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotBlank(message = "Customer name is required")
        String customerName,
        @NotBlank(message = "Customer email is required")
        String customerEmail,
        @NotNull(message = "Product IDs are required")
        @NotEmpty(message = "Product IDs cannot be empty")
        List<Long> productIds
) {}
