package com.pragma.powerup.application.handler.impl;


import com.pragma.powerup.application.dto.response.ListarRestauranteResponseDto;
import com.pragma.powerup.application.handler.IListarRestauranteHandler;
import com.pragma.powerup.domain.api.IListarRestauranteServicePort;
import com.pragma.powerup.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class ListarRestauranteHandler implements IListarRestauranteHandler {

    private final IListarRestauranteServicePort listarRestauranteServicePort;

    @Override
    public Page<ListarRestauranteResponseDto> listarRestaurante(PageRequest params) {

        Page<Restaurante> listarRestaurante = listarRestauranteServicePort.listarRestaurante(params);
        return listarRestaurante.map(this::toRestauranteResponse);
    }

    private ListarRestauranteResponseDto toRestauranteResponse(Restaurante restaurante) {

        ListarRestauranteResponseDto dto = new ListarRestauranteResponseDto();
        dto.setNombre(restaurante.getNombre());
        dto.setUrlImagen(restaurante.getUrlLogo());
        return dto;


    }
}
