package com.nttdata.pedidos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private LocalDate data;

    @ElementCollection
    private List<Long> produtos;

    private BigDecimal total;

    // Getters e setters omitidos por brevidade
}
