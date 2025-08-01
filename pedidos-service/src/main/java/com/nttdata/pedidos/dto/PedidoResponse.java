package com.nttdata.pedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PedidoResponse(
    Long id,
    String cliente,
    LocalDate data,
    List<ProdutoResponse> produtos,
    BigDecimal total
) {}
