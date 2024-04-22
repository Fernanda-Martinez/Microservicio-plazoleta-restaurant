package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlatoPedidoRepository extends JpaRepository<PlatoPedidoEntity, Integer> {
}
