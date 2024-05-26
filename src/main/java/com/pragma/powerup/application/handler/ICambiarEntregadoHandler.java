package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;

public interface ICambiarEntregadoHandler {
    AsignarPedidoResponseDto cambiarEntregado(int idEmpleado, int idPedido, String pin);
}
