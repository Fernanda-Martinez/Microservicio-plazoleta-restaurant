package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICambiarEstadoPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.ICambiarEstadoPlatoPersistencePort;

public class CambiarEstadoPlatoUseCase implements ICambiarEstadoPlatoServicePort {

    private final ICambiarEstadoPlatoPersistencePort habilitarDeshabilitarPlatoPersistencePort;

    public CambiarEstadoPlatoUseCase(ICambiarEstadoPlatoPersistencePort habilitarDeshabilitarPlatoPersistencePort) {
        this.habilitarDeshabilitarPlatoPersistencePort = habilitarDeshabilitarPlatoPersistencePort;
    }

    @Override
    public Plato cambiarEstado(int id) {
        return this.habilitarDeshabilitarPlatoPersistencePort.cambiarEstado(id);
    }
}
