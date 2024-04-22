package com.pragma.powerup.application.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ListarPedidoResponseDto {
    int id;
    String estado;
    Date fecha;
    int idChef;
    int idCliente;
    int idRestaurante;
}
