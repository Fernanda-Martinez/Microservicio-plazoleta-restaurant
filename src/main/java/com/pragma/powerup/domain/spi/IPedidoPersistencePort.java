package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.infrastructure.exception.ExceptionMessage;

public interface IPedidoPersistencePort {
    Pedido registrar(Pedido pedidoRegistrado) throws ExceptionMessage;
}
