package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Pedido;

public interface IPedidoServicePort {
    Pedido registrar(Pedido pedidoRegistrado);
}
