package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Plato {
    private String nombre;
    private int idCategoria;
    private String descripcion;
    private int precio;
    private int idRestaurante;
    private String urlImagen;
    private Boolean activo;
}