package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IAsignarPedidoPersistencePort;


public class AsignarPedidoUseCase implements IAsignarPedidoServicePort {
    private final IAsignarPedidoPersistencePort asignarPedidoPersistencePort;

    public AsignarPedidoUseCase(IAsignarPedidoPersistencePort asignarPedidoPersistencePort) {
        this.asignarPedidoPersistencePort = asignarPedidoPersistencePort;
    }

    @Override
    public Pedido asignar(int idEmpleado, int idPedido) {
        return this.asignarPedidoPersistencePort.asignar(idEmpleado, idPedido);
    }
}
