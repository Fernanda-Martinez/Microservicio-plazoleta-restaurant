package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearTrazaRequestDto;
import com.pragma.powerup.application.dto.response.CancelarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.ICancelarPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.api.ICancelarPedidoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelarPedidoHandler implements ICancelarPedidoHandler {

    private final ICancelarPedidoServicePort cancelarPedidoServicePort;
    private final IAsignarPedidoServicePort asignarPedidoServicePort;
    private final IHttpRequestServicePort httpRequestServicePort;
    private final IUsuarioFeignClient usuarioFeignClient;
    private final ITrazaFeignClient trazaFeignClient;

    @Override
    public CancelarPedidoResponseDto cancelarPedido(int idEmpleado, int idPedido) {

        String response = cancelarPedidoServicePort.cancelarPedido(idEmpleado, idPedido);
        CancelarPedidoResponseDto dto = new CancelarPedidoResponseDto();
        dto.setMessage(response);

        Pedido pedido = asignarPedidoServicePort.buscarPedido(idPedido);
        CrearTrazaRequestDto trazaRequestDto = crearTrazaRequestDto(pedido);
        trazaFeignClient.crear(httpRequestServicePort.getToken(), trazaRequestDto.getIdEmpleado(),trazaRequestDto.getIdPedido(), Integer.parseInt(trazaRequestDto.getIdCliente()),
                trazaRequestDto.getCorreoEmpleado(),trazaRequestDto.getCorreoCliente(),trazaRequestDto.getNuevoEstado());
        return dto;
    }


    private CrearTrazaRequestDto crearTrazaRequestDto(Pedido pedido){
        UserInfoResponseDto cliente = usuarioFeignClient.getUser(httpRequestServicePort.getToken(), pedido.getIdCliente());

        com.pragma.powerup.application.dto.request.CrearTrazaRequestDto requestDto = new CrearTrazaRequestDto();
        requestDto.setIdEmpleado(pedido.getIdChef());
        requestDto.setIdPedido(pedido.getId());
        requestDto.setIdCliente(String.valueOf(pedido.getIdCliente()));
        requestDto.setCorreoCliente(cliente.getEmail());
        requestDto.setNuevoEstado(pedido.getEstado());

        return requestDto;
    }


}
