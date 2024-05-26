package com.pragma.powerup.infrastructure.exception;

public class UsuarioNoPropietario extends RuntimeException {
    public UsuarioNoPropietario(String message){
        super(message);
    }
}
