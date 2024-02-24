package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.ListarRestauranteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IListarRestauranteHandler {

    Page<ListarRestauranteResponseDto> listarRestaurante(PageRequest params);
}
