package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter

public class CrearRestauranteRequestDto {
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$", message = "El nombre del restaurante no puede consistir solo de números")
    private String nombre;
    private String direccion;
    private int idPropietario;
    @Size(max = 13, message = "El número de celular no es válido")
    @Pattern(regexp = "[0-9+]+", message = "El numero de celular solo debe contener caracteres numericos, con excepcion del simbolo +")
    private String telefono;
    private String urlLogo;
    private int nit;
    private int idAdmin;


}
