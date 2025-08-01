package com.nttdata.pedidos.dto;

import java.util.List;

public record OrderRequest(List<Long> productIds) {}
