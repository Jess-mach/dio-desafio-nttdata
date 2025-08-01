package com.nttdata.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ProdutoRequest {

    @Schema(description = "Nome do produto", example = "Notebook")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM e SSD")
    private String descricao;

    @Schema(description = "Preço do produto", example = "2999.90")
    private BigDecimal preco;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
}
