package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.CancelarPedidoResponseDto;

public interface ICancelarPedidoHandler {
    CancelarPedidoResponseDto cancelarPedido(int idEmpleado, int idPedido);
}
