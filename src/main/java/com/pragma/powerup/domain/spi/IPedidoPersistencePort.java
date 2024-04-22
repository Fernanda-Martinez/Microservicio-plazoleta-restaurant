package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;

public interface IPedidoPersistencePort {
    Pedido registrar(Pedido pedidoRegistrado);
}
