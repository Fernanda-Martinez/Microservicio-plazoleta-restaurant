package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Restaurante {

    private int id;
    private String nombre;
    private String direccion;
    private int idPropietario;
    private String telefono;
    private String urlLogo;
    private int nit;

}
