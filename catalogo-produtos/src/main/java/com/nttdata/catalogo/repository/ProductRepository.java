package com.nttdata.catalogo.repository;

import com.nttdata.catalogo.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
