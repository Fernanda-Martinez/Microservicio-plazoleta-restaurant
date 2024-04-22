package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pedidos_platos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PlatoPedidoEntity {

    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id = 0;
    private int idPedidos;
    private int idPlato;
    private int cantidad;

}
