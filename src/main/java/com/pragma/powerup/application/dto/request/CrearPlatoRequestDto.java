package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

@Getter
@Setter

public class CrearPlatoRequestDto {

    private String nombre;
    private int idCategoria;
    private String descripcion;
    @Digits(integer = 10, fraction = 0, message = "El número debe ser un entero positivo")
    @Positive(message = "El número debe ser positivo y mayor a 0")
    private int precio;
    private int idRestaurante;
    private String urlImagen;
    private int idPropietario;
}
