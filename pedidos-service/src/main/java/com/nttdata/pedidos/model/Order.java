package com.nttdata.pedidos.model;

import com.nttdata.pedidos.dto.ProductResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record Order(UUID id, List<ProductResponse> products, BigDecimal totalAmount) {}
