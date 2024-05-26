package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICambiarEstadoPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.spi.ICambiarEstadoPedidoPersistencePort;

public class CambiarEstadoPedidoUseCase implements ICambiarEstadoPedidoServicePort {
    private final ICambiarEstadoPedidoPersistencePort cambiarEstadoPlatoPersistencePort;

    public CambiarEstadoPedidoUseCase(ICambiarEstadoPedidoPersistencePort cambiarEstadoPlatoPersistencePort) {
        this.cambiarEstadoPlatoPersistencePort = cambiarEstadoPlatoPersistencePort;
    }


    @Override
    public Pedido cambiarEstadoPedido(int idEmpleado, int idPedido) {
        return cambiarEstadoPlatoPersistencePort.cambiarEstadoPedido(idEmpleado, idPedido);
    }

    @Override
    public Pedido asignarPin(int idPedido, String pin) {
        return cambiarEstadoPlatoPersistencePort.asignarPin(idPedido, pin);
    }

    @Override
    public Restaurante obtenerRestaurante(int idRestaurante) {
        return cambiarEstadoPlatoPersistencePort.obtenerRestaurante(idRestaurante);
    }
}
