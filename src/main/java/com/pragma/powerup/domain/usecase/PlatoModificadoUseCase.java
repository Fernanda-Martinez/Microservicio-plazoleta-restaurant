package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPlatoModServicePort;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.IPlatoModPersistencePort;


public class PlatoModificadoUseCase implements IPlatoModServicePort {

    private final IPlatoModPersistencePort platoModPersistencePort;

    public PlatoModificadoUseCase(IPlatoModPersistencePort platoModPersistencePort) {
        this.platoModPersistencePort = platoModPersistencePort;
    }


    @Override
    public Plato modificar(Plato platoModificado) {
        return this.platoModPersistencePort.modificar(platoModificado);
    }
}
