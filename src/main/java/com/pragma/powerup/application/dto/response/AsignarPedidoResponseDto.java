package com.pragma.powerup.application.dto.response;

import lombok.Data;

@Data
public class AsignarPedidoResponseDto {
    int idEmpleado;
    int idPedido;
    int idRestaurante;
    String estado;
}
