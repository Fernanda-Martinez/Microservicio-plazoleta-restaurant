package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.*;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PlatoJpaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;

    //Restaurante
    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(restaurantRepository, restauranteEntityMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {

        return new RestauranteUseCase(restaurantePersistencePort());
    }

    //Plato
    @Bean
    public IPlatoPersistencePort platoPersistencePort() {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Bean
    public IPlatoServicePort platoServicePort() {

        return new PlatoUseCase(platoPersistencePort());
    }

    //Modificar Plato
    @Bean
    public IPlatoModPersistencePort platoModPersistencePort () {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Bean
    public IPlatoModServicePort platoModServicePort() {

        return new PlatoModificadoUseCase(platoModPersistencePort());
    }

    //Cambiar estado Plato
    @Bean
    public ICambiarEstadoPlatoPersistencePort cambiarEstadoPlatoPersistencePort () {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Bean
    public ICambiarEstadoPlatoServicePort cambiarEstadoPlatoServicePort() {
        return new CambiarEstadoPlatoUseCase(cambiarEstadoPlatoPersistencePort());
    }

    //Listar restaurantes

    @Bean
    public IListarRestaurantePersistencePort listarRestaurantePersistencePort(){
        return new RestauranteJpaAdapter(restaurantRepository, restauranteEntityMapper);
    }

    @Bean
    public IListarRestauranteServicePort listarRestauranteServicePort(){
        return new ListarRestauranteUseCase(listarRestaurantePersistencePort());
    }

    //Listar platos

    @Bean
    public IListarPlatoPersistencePort listarPlatoPersistencePort(){
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Bean
    public IListarPlatoServicePort listarPlatoServicePort(){
        return new ListarPlatoUseCase(listarPlatoPersistencePort());
    }


}