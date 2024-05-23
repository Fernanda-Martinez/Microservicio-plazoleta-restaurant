package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.infrastructure.exception.ExceptionMessage;

public interface IPedidoServicePort {
    Pedido registrar(Pedido pedidoRegistrado) throws ExceptionMessage;
}
