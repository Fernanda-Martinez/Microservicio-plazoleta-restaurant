package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.ListarPlatosResponseDto;
import com.pragma.powerup.application.handler.IListarPlatoHandler;
import com.pragma.powerup.domain.api.IListarPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class ListarPlatoHandler implements IListarPlatoHandler {

    private final IListarPlatoServicePort listarPlatoServicePort;


    @Override
    public Page<ListarPlatosResponseDto> listarPlatos(PageRequest pageRequest, Integer idCategoria, int idRestaurante) {
        Page<Plato> listarPlato = listarPlatoServicePort.listarPlato(pageRequest,idCategoria,idRestaurante);

        return listarPlato.map(this::toPlatoResponse);
    }



    public ListarPlatosResponseDto toPlatoResponse(Plato plato) {

        ListarPlatosResponseDto dto = new ListarPlatosResponseDto();
        dto.setNombre(plato.getNombre());
        dto.setUrlImagen(plato.getUrlImagen());
        return dto;


    }
}
