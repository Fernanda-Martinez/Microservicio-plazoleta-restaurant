package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IPedidoPersistencePort;

public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
    }

    @Override
    public Pedido registrar(Pedido pedidoRegistrado) {
        return this.pedidoPersistencePort.registrar(pedidoRegistrado);
    }
}
