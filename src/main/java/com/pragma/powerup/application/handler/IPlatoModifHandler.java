package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.ModificarPlatoRequestDto;
import com.pragma.powerup.application.dto.response.ModificarPlatoResponseDto;

public interface IPlatoModifHandler {

    ModificarPlatoResponseDto modificarPlato(ModificarPlatoRequestDto modificarPlatoRequestDto);
}
