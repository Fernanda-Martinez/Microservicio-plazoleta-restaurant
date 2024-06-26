package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.ModificarPlatoRequestDto;
import com.pragma.powerup.application.dto.response.ModificarPlatoResponseDto;
import com.pragma.powerup.application.handler.IPlatoModifHandler;
import com.pragma.powerup.application.mapper.IPlatoModifRequestMapper;
import com.pragma.powerup.application.mapper.IPlatoModifResponseMapper;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IPlatoModServicePort;
import com.pragma.powerup.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class PlatoModifHandler implements IPlatoModifHandler {

    private final IPlatoModServicePort platoModServicePort;
    private final IPlatoModifRequestMapper platoModifRequestMapper;
    private final IPlatoModifResponseMapper platoModifResponseMapper;
    private final IUsuarioFeignClient usuarioFeignClient;
    private final IHttpRequestServicePort httpRequestServicePort;

    @Override
    public ModificarPlatoResponseDto modificarPlato(ModificarPlatoRequestDto modificarPlatoRequestDto) {
        if (usuarioFeignClient.validateOwnerRole(
                httpRequestServicePort.getToken(),
                modificarPlatoRequestDto.getIdPropietario())) {
            Plato plato = platoModifRequestMapper.toPlatoModif(modificarPlatoRequestDto);

            Plato response = platoModServicePort.modificar(plato, modificarPlatoRequestDto.getIdPropietario());
            return platoModifResponseMapper.toResponse(response);
        }
        return null;
    }
}


