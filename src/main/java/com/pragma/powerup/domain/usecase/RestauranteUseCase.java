package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;

public class RestauranteUseCase implements IRestauranteServicePort {

    private final IRestaurantePersistencePort restaurantePersistencePort;

    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort){
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    @Override
    public Restaurante crear(Restaurante restaurante) {
        return this.restaurantePersistencePort.crear(restaurante);


    }
}
