package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;

public interface IAsignarPedidoHandler {
    AsignarPedidoResponseDto asignar(int idEmpleado, int idPedido);
}
