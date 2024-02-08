package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class CrearPlatoResponseDto {
    private String nombre;
    private int idCategoria;
    private String descripcion;
    private int precio;
    private int idRestaurante;
    private String urlImagen;
    private Boolean activo;
}
