package com.nttdata.catalogo.dto;

import java.math.BigDecimal;

public class ProdutoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;

    public ProdutoResponse(Long id, String nome, String descricao, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
}
