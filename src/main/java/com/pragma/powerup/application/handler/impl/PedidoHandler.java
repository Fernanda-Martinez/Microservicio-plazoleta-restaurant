package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearTrazaRequestDto;
import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.response.RealizarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.IPedidoHandler;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.infrastructure.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PedidoHandler implements IPedidoHandler {

    private final IPedidoServicePort pedidoServicePort;
    private final ITrazaFeignClient trazaFeignClient;
    private final IUsuarioFeignClient usuarioFeignClient;
    private final IHttpRequestServicePort httpRequestServicePort;


    @Override
    public RealizarPedidoResponseDto registrar(RealizarPedidoRequestDto request) throws ExceptionMessage {


        Pedido pedido = new Pedido();
        pedido.setIdCliente(request.getIdCliente());
        pedido.setIdRestaurante(request.getIdRestaurante());
        pedido.setPlatoRequestDtoList(request.getPlatoRequestDtoList());
        Pedido response = pedidoServicePort.registrar(pedido);

        CrearTrazaRequestDto trazaRequestDto = crearTrazaRequestDto(response);
        trazaFeignClient.crear(httpRequestServicePort.getToken(), trazaRequestDto.getIdEmpleado(),trazaRequestDto.getIdPedido(), Integer.parseInt(trazaRequestDto.getIdCliente()),
                trazaRequestDto.getCorreoEmpleado(),trazaRequestDto.getCorreoCliente(),trazaRequestDto.getNuevoEstado());

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

    private CrearTrazaRequestDto crearTrazaRequestDto(Pedido pedido){
        UserInfoResponseDto cliente = usuarioFeignClient.getUser(httpRequestServicePort.getToken(), pedido.getIdCliente());

        CrearTrazaRequestDto requestDto = new CrearTrazaRequestDto();
        requestDto.setIdEmpleado(pedido.getIdChef());
        requestDto.setIdPedido(pedido.getId());
        requestDto.setIdCliente(String.valueOf(pedido.getIdCliente()));
        requestDto.setCorreoEmpleado("");
        requestDto.setCorreoCliente(cliente.getEmail());
        requestDto.setNuevoEstado(pedido.getEstado());

        return requestDto;
    }
}
