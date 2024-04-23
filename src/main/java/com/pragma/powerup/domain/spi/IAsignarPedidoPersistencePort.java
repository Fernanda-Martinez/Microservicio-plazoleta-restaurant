package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;

public interface IAsignarPedidoPersistencePort {
    Pedido asignar(int idEmpleado, int idPedido);
}
