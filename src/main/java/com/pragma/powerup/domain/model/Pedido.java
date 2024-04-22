package com.pragma.powerup.domain.model;

import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Pedido {

    private int id = 0;
    private int idCliente = 0;
    private int idRestaurante = 0;
    private String estado = "";
    private List<PlatoRequestDto> platoRequestDtoList = new ArrayList<>();
}
