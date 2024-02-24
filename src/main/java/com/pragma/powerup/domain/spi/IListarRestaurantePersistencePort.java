package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IListarRestaurantePersistencePort {
    Page<Restaurante> listarRestaurante(PageRequest pageRequest);
}
