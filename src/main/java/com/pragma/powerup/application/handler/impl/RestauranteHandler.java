package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearRestauranteRequestDto;
import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;
import com.pragma.powerup.application.handler.IRestauranteHandler;
import com.pragma.powerup.application.mapper.IRestauranteRequestMapper;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
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
    private final IUsuarioFeignClient usuarioFeignClient;
    private final IHttpRequestServicePort httpRequestServicePort;

    @Override

    public CrearRestauranteResponseDto crear(CrearRestauranteRequestDto crearRestauranteRequestDto) {
        if(usuarioFeignClient.validateAdminRole(
                httpRequestServicePort.getToken(),
                crearRestauranteRequestDto.getIdAdmin())){
            Restaurante restaurante = restauranteRequestMapper.toRestaurante(crearRestauranteRequestDto);
            restauranteServicePort.crear(restaurante);

            CrearRestauranteResponseDto response = new CrearRestauranteResponseDto();

            response.setNombre(restaurante.getNombre());
            response.setNit(restaurante.getNit());
            return response;
        }
        return null;
    }

}
