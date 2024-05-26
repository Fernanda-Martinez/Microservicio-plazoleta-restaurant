package com.pragma.powerup.application.handler.impl;


import com.pragma.powerup.application.dto.response.CambiarEstadoPlatoResponseDto;
import com.pragma.powerup.application.handler.IPlatoEstadoHandler;
import com.pragma.powerup.application.mapper.IPlatoCambiarEstadoResponseMapper;
import com.pragma.powerup.domain.api.ICambiarEstadoPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class PlatoEstadoHandler implements IPlatoEstadoHandler {

    private final ICambiarEstadoPlatoServicePort habilitarDeshabilitarPlatoServicePort ;
    private final IPlatoCambiarEstadoResponseMapper platoCambiarEstadoResponseMapper;


    @Override
    public CambiarEstadoPlatoResponseDto cambiarEstadoPlato(int id, int idPropietario) {

        Plato response = habilitarDeshabilitarPlatoServicePort.cambiarEstado(id, idPropietario) ;
        return platoCambiarEstadoResponseMapper.toResponse(response);
    }
}
