package com.nttdata.catalogo.repository;

import com.nttdata.catalogo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
