package com.nttdata.catalogo.dto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String name,
        String description,
        BigDecimal price) {}


