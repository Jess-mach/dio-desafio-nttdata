package com.nttdata.produtos.dto;

import java.math.BigDecimal;

public record ProdutoRequest(String nome, String descricao, BigDecimal preco) {}
