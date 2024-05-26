package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.response.ListarPedidoResponseDto;
import com.pragma.powerup.application.handler.IListarPedidoHandler;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IListarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ListarPedidoHandler implements IListarPedidoHandler {

    private final IListarPedidoServicePort listarPedidoServicePort;
    private final IHttpRequestServicePort httpRequestServicePort;
    private final IUsuarioFeignClient usuarioFeignClient;

    @Override
    public Page<ListarPedidoResponseDto> listarPedidos(int idEmpleado, int idRestaurante, String estado, PageRequest pageRequest) {

        if(usuarioFeignClient.validateEmployeeRestaurant(
                httpRequestServicePort.getToken(),
                idEmpleado,
                idRestaurante
        )){
            Page<Pedido> response = listarPedidoServicePort.listarPedidos(idEmpleado, idRestaurante, estado, pageRequest);

            return response.map(this::toDto);
        }

        return null;
    }

    private ListarPedidoResponseDto toDto(Pedido pedido){
        ListarPedidoResponseDto dto = new ListarPedidoResponseDto();
        dto.setId(pedido.getId());
        dto.setEstado(pedido.getEstado());
        dto.setIdRestaurante(pedido.getIdRestaurante());
        dto.setFecha(pedido.getFecha());

        return dto;
    }

}
