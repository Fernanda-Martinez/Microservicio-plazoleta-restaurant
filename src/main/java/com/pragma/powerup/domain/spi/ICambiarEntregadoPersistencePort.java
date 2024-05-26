package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;

public interface ICambiarEntregadoPersistencePort {
    Pedido cambiarEntregado(int idEmpleado, int idPedido, String pin);
}
