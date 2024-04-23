package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Pedido;

public interface IAsignarPedidoServicePort {
    Pedido asignar(int idEmpleado, int idPedido);
}
