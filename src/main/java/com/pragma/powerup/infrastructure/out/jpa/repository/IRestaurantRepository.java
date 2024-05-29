package com.pragma.powerup.infrastructure.out.jpa.repository;


import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestauranteEntity, Integer> {
    @Query("SELECT p FROM RestauranteEntity p WHERE p.nombre = :nombre")
    Optional<RestauranteEntity> buscarNombreRestaurante(
            @Param("nombre") String nombre
    );
    @Query("SELECT p FROM RestauranteEntity p WHERE p.nit = :nit")
    Optional<RestauranteEntity> buscarNitRestaurante(
            @Param("nit") Integer nit
    );
}
