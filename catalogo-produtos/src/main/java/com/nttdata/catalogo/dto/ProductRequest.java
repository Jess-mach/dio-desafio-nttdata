package com.nttdata.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Schema(description = "Nome do produto", example = "Notebook")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM e SSD")
    private String description;

    @NotNull(message = "O preço do produto é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço do produto deve ser maior ou igual a zero")
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
