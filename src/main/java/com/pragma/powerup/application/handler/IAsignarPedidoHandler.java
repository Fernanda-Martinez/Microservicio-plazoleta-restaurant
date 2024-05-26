package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IAsignarPedidoHandler {
    Page<AsignarPedidoResponseDto> asignar(int idEmpleado, int idPedido, String estado,PageRequest pageRequest);
}
