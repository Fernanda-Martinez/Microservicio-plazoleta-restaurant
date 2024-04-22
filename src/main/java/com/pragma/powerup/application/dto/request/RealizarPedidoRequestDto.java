package com.pragma.powerup.application.dto.request;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@ApiModel(description = "DTO para realizar un pedido")
public class RealizarPedidoRequestDto {

    @ApiModelProperty(value = "ID del cliente", example = "1")
    private int idCliente;

    @ApiModelProperty(value = "ID del restaurante", example = "1")
    private int idRestaurante;

    @ApiModelProperty(value = "Lista de platos del pedido")
    private List<PlatoRequestDto> platoRequestDtoList;
}
