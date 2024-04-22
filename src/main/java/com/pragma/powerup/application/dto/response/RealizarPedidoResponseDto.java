package com.pragma.powerup.application.dto.response;

import lombok.Data;

@Data
public class RealizarPedidoResponseDto {

    private int idPedido;
    private int idCliente;
    private String estado;
    private int idRestautante;

}
