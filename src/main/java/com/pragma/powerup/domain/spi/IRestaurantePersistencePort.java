package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurante;

public interface IRestaurantePersistencePort {

    Restaurante crear(Restaurante restaurante);
}
