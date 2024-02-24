package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlatoRepository extends JpaRepository<PlatoEntity, Integer> {

    @Query("SELECT p FROM PlatoEntity p WHERE (:idCategoria IS NULL OR p.idCategoria = :idCategoria) AND p.idRestaurante = :idRestaurante")
    Page<PlatoEntity> buscarCategoriayRestaurante(
            PageRequest pageRequest,
            @Param("idCategoria") Integer idCategoria,
            @Param("idRestaurante") Integer idRestaurante
    );
}

