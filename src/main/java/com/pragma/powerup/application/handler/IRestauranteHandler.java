package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CrearRestauranteRequestDto;
import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;

public interface IRestauranteHandler {

    CrearRestauranteResponseDto crearRestaurante(CrearRestauranteRequestDto restauranteRequestDto);

}
