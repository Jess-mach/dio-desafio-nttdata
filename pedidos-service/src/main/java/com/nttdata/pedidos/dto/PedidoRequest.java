package com.nttdata.pedidos.dto;

import java.util.List;

public record PedidoRequest(
    String cliente,
    List<Long> produtos
) {}
