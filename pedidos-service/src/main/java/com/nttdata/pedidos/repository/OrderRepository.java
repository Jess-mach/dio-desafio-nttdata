package com.nttdata.pedidos.repository;

import com.nttdata.pedidos.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
