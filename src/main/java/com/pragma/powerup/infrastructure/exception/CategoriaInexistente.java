package com.pragma.powerup.infrastructure.exception;

public class CategoriaInexistente extends RuntimeException{
    public CategoriaInexistente(String message){
        super(message);
    }
}
