package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;


public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort){
        this.platoPersistencePort = platoPersistencePort;
    }

    @Override
    public Plato crear(Plato plato, int idPropietario) {
        return this.platoPersistencePort.crear(plato, idPropietario);
    }
}
