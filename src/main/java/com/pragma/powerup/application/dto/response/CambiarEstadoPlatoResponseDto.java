package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambiarEstadoPlatoResponseDto {

    private String nombre;
    private int idRestaurante;
    private Boolean activo;

}
