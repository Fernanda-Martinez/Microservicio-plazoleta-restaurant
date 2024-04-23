package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICancelarPedidoServicePort;
import com.pragma.powerup.domain.spi.ICancelarPedidoPersistencePort;

public class CancelarPedidoUseCase implements ICancelarPedidoServicePort {
    private final ICancelarPedidoPersistencePort cancelarPedidoPersistencePort;

    public CancelarPedidoUseCase(ICancelarPedidoPersistencePort cancelarPedidoPersistencePort) {
        this.cancelarPedidoPersistencePort = cancelarPedidoPersistencePort;
    }

    @Override
    public String cancelarPedido(int idCliente, int idPedido) {
        return cancelarPedidoPersistencePort.cancelarPedido(idCliente, idPedido);
    }
}
