package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.CambiarEstadoPlatoResponseDto;

public interface IPlatoEstadoHandler {
    CambiarEstadoPlatoResponseDto cambiarEstadoPlato(int id, int idPropietario);
}
