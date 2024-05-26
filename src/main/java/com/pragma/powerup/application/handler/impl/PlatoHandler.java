package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearPlatoRequestDto;
import com.pragma.powerup.application.dto.response.CrearPlatoResponseDto;
import com.pragma.powerup.application.handler.IPlatoHandler;
import com.pragma.powerup.application.mapper.IPlatoRequestMapper;
import com.pragma.powerup.application.mapper.IPlatoResponseMapper;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class PlatoHandler implements IPlatoHandler {

    private final IPlatoServicePort platoServicePort;
    private final IPlatoRequestMapper platoRequestMapper;
    private final IPlatoResponseMapper platoResponseMapper;
    private final IUsuarioFeignClient usuarioFeignClient;
    private final IHttpRequestServicePort httpRequestServicePort;

    @Override
    public CrearPlatoResponseDto crearPlato(CrearPlatoRequestDto platoRequestDto) {

        if(usuarioFeignClient.validateOwnerRole(
                httpRequestServicePort.getToken(),
                platoRequestDto.getIdPropietario())){
            Plato plato = platoRequestMapper.toPlato(platoRequestDto);
            plato.setActivo(true);
            platoServicePort.crear(plato, platoRequestDto.getIdPropietario());

            return platoResponseMapper.toResponse(plato);
        }
        return null;
    }
}
