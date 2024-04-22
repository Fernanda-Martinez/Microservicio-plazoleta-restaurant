package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.response.RealizarPedidoResponseDto;

public interface IPedidoHandler {
    RealizarPedidoResponseDto registrar(RealizarPedidoRequestDto request);
}
