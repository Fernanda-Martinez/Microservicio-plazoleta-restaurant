package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.CancelarPedidoResponseDto;
import com.pragma.powerup.application.handler.ICancelarPedidoHandler;
import com.pragma.powerup.domain.api.ICancelarPedidoServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelarPedidoHandler implements ICancelarPedidoHandler {

    private final ICancelarPedidoServicePort cancelarPedidoServicePort;
    @Override
    public CancelarPedidoResponseDto cancelarPedido(int idEmpleado, int idPedido) {
        String response = cancelarPedidoServicePort.cancelarPedido(idEmpleado, idPedido);
        CancelarPedidoResponseDto dto = new CancelarPedidoResponseDto();
        dto.setMessage(response);

        return dto;
    }
}
