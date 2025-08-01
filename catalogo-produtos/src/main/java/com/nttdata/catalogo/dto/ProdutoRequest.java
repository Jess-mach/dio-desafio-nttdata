package com.nttdata.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ProdutoRequest {

    @Schema(description = "Nome do produto", example = "Notebook")
    private String name;

    @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM e SSD")
    private String description;

    @Schema(description = "Preço do produto", example = "2999.90")
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
