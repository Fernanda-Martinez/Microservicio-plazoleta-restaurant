package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.response.RealizarPedidoResponseDto;
import com.pragma.powerup.application.handler.IPedidoHandler;
import com.pragma.powerup.application.mapper.IPedidoRequestMapper;
import com.pragma.powerup.application.mapper.IPedidoResponseMapper;
import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoHandler implements IPedidoHandler {

    private final IPedidoServicePort pedidoServicePort;
    private final IPedidoRequestMapper pedidoRequestMapper;
    private final IPedidoResponseMapper pedidoResponseMapper;
    @Override
    public RealizarPedidoResponseDto registrar(RealizarPedidoRequestDto request) {
        Pedido pedido = new Pedido();
        pedido.setIdCliente(request.getIdCliente());
        pedido.setIdRestaurante(request.getIdRestaurante());
        pedido.setPlatoRequestDtoList(request.getPlatoRequestDtoList());


        Pedido response = pedidoServicePort.registrar(pedido);

        return toDto(response);
    }

    private RealizarPedidoResponseDto toDto(Pedido pedido){
        RealizarPedidoResponseDto response = new RealizarPedidoResponseDto();
        response.setIdPedido(pedido.getId());
        response.setIdCliente(pedido.getIdCliente());
        response.setIdRestautante(pedido.getIdRestaurante());
        response.setEstado(pedido.getEstado());

        return response;
    }
}
