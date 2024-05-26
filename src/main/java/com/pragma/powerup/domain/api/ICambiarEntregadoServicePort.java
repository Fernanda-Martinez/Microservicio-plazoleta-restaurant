package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Pedido;

public interface ICambiarEntregadoServicePort {
    Pedido cambiarEntregado(int idEmpleado, int idPedido, String pin);
}
