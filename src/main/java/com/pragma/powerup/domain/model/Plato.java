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
    private int id = 0;
    private String nombre = "";
    private int idCategoria = 0;
    private String descripcion = "";
    private int precio = 0;
    private int idRestaurante = 0;
    private String urlImagen = "";
    private Boolean activo = false;
}