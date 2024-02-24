package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.ListarPlatosResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IListarPlatoHandler {

    Page<ListarPlatosResponseDto> listarPlatos (PageRequest pageRequest, Integer idCategoria, int idRestaurante);
}
