package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import com.pragma.powerup.application.handler.IAsignarPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AsignarPedidoHandler implements IAsignarPedidoHandler {
    private final IAsignarPedidoServicePort asignarPedidoServicePort;

    @Override
    public AsignarPedidoResponseDto asignar(int idEmpleado, int idPedido) {
        Pedido response = asignarPedidoServicePort.asignar(idEmpleado, idPedido);
        return toDto(response);
    }

    private AsignarPedidoResponseDto toDto(Pedido pedido){
        AsignarPedidoResponseDto res = new AsignarPedidoResponseDto();
        res.setIdEmpleado(pedido.getIdChef());
        res.setIdPedido(pedido.getId());
        res.setIdRestaurante(pedido.getIdRestaurante());
        res.setEstado(pedido.getEstado());

        return res;
    }
}
