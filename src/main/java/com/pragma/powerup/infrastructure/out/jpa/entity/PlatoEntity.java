package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "platos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PlatoEntity {

    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(length = 45)
    private String nombre;
    private int idCategoria;
    @Column(length = 45)
    private String descripcion;
    private int precio;
    private int idRestaurante;
    @Column(length = 45)
    private String urlImagen;
    private Boolean activo;
}
