package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.CambiarEstadoPedidoResponseDto;

public interface ICambiarEstadoPedidoHandler {

    CambiarEstadoPedidoResponseDto cambiarEstadoPedido(int idEmpleado, int idPedido);
}
