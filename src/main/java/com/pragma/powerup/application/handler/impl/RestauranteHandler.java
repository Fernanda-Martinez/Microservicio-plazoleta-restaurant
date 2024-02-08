package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CrearRestauranteRequestDto;
import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;
import com.pragma.powerup.application.handler.IRestauranteHandler;
import com.pragma.powerup.application.mapper.IRestauranteRequestMapper;
import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class RestauranteHandler implements IRestauranteHandler {

    private final IRestauranteServicePort restauranteServicePort;
    private final IRestauranteRequestMapper restauranteRequestMapper;

    @Override
    public CrearRestauranteResponseDto crearRestaurante(CrearRestauranteRequestDto restauranteRequestDto) {

        Restaurante restaurante = restauranteRequestMapper.toRestaurante(restauranteRequestDto);

        Restaurante response = restauranteServicePort.crear(restaurante) ;
        CrearRestauranteResponseDto dto = new CrearRestauranteResponseDto();
        dto.setNit(response.getNit());
        dto.setNombre(response.getNombre());

        return dto;
    }
}
