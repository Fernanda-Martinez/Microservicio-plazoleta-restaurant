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

    private int id = 0;
    private String nombre = "";
    private String direccion = "";
    private int idPropietario = 0;
    private String telefono = "";
    private String urlLogo = "";
    private int nit = 0;

}
