package com.nttdata.pedidos.dto;

import java.util.List;


public record OrderResponse(
        String id,
        String customerName,
        String customerEmail,
        List<ProductResponse> products
) {

}
