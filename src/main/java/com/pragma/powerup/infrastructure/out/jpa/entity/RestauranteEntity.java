package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RestauranteEntity {

    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(length = 45)
    private String nombre;
    @Column(length = 45)
    private String direccion;

    private int idPropietario;
    @Column(length = 45)
    private String telefono;
    @Column(length = 45)
    private String urlLogo;

    private int nit;
}
