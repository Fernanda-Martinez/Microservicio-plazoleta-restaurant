package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.ListarPedidoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IListarPedidoHandler {
    Page<ListarPedidoResponseDto> listarPedidos(int idEmpleado, int idRestaurante, String estado, PageRequest pageRequest);
}
