package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IListarRestauranteServicePort;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.spi.IListarRestaurantePersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public class ListarRestauranteUseCase implements IListarRestauranteServicePort {

    private final IListarRestaurantePersistencePort listarRestaurantePersistencePort;

    public ListarRestauranteUseCase(IListarRestaurantePersistencePort listarRestaurantePersistencePort) {
        this.listarRestaurantePersistencePort = listarRestaurantePersistencePort;
    }

    @Override
    public Page<Restaurante> listarRestaurante(PageRequest pageRequest) {
        return this.listarRestaurantePersistencePort.listarRestaurante(pageRequest);
    }
}
