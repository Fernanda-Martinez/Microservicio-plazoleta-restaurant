package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CrearPlatoRequestDto;
import com.pragma.powerup.application.dto.response.CrearPlatoResponseDto;

public interface IPlatoHandler {
    CrearPlatoResponseDto crearPlato(CrearPlatoRequestDto platoRequestDto);
}
