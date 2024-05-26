package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICambiarEntregadoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.ICambiarEntregadoPersistencePort;

public class CambiarEntregadoUseCase implements ICambiarEntregadoServicePort {

    private final ICambiarEntregadoPersistencePort cambiarEntregadoPersistencePort;

    public CambiarEntregadoUseCase(ICambiarEntregadoPersistencePort cambiarEntregadoPersistencePort) {
        this.cambiarEntregadoPersistencePort = cambiarEntregadoPersistencePort;
    }

    @Override
    public Pedido cambiarEntregado(int idEmpleado, int idPedido, String pin) {
        return cambiarEntregadoPersistencePort.cambiarEntregado(idEmpleado, idPedido, pin);
    }
}
