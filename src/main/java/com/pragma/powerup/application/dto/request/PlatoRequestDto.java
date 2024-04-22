package com.pragma.powerup.application.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "DTO para un plato en el pedido")
@Data
public class PlatoRequestDto {
    @ApiModelProperty(value = "ID del plato", example = "1")
    private int idPlato;
    @ApiModelProperty(value = "Cantidad del plato", example = "2")
    private int cantidad;
}
