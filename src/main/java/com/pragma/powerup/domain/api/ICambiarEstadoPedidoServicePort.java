package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.Restaurante;

public interface ICambiarEstadoPedidoServicePort {
    Pedido cambiarEstadoPedido(int idEmpleado, int idPedido);

    Pedido asignarPin(int idPedido, String pin);

    Restaurante obtenerRestaurante(int idRestaurante);
}
