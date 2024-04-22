package com.pragma.powerup.domain.model;

import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private int id = 0;
    private int idCliente = 0;
    private int idRestaurante = 0;
    private String estado = "";
    private Date fecha;
    private int idChef = 0;
    private List<PlatoRequestDto> platoRequestDtoList = new ArrayList<>();
}
