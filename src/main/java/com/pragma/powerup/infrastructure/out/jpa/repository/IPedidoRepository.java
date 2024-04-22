package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPedidoRepository extends JpaRepository<PedidoEntity,Integer> {
    @Query("SELECT p FROM PedidoEntity p WHERE (:estado IS NULL OR p.estado = :estado) AND p.idRestaurante = :idRestaurante")
    Page<PedidoEntity> buscarEstadoPedido(
            PageRequest pageRequest,
            @Param("estado") String estado,
            @Param("idRestaurante") Integer idRestaurante
    );

}
