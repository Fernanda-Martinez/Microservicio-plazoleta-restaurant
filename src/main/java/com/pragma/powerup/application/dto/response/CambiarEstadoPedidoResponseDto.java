package com.pragma.powerup.application.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CambiarEstadoPedidoResponseDto {
    int idEmpleado;
    int idPedido;
    int idRestaurante;
    String estado;
}
