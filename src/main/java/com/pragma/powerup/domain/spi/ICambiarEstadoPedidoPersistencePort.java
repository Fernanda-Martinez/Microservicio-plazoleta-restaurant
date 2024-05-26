package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.Restaurante;

public interface ICambiarEstadoPedidoPersistencePort {
    Pedido cambiarEstadoPedido(int idEmpleado, int idPedido);

    Pedido asignarPin(int idPedido, String pin);

    Restaurante obtenerRestaurante(int idRestuarante);
}
