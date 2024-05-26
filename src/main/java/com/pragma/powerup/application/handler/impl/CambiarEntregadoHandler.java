package com.pragma.powerup.application.handler.impl;


import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearTrazaRequestDto;
import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.ICambiarEntregadoHandler;
import com.pragma.powerup.domain.api.ICambiarEntregadoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CambiarEntregadoHandler implements ICambiarEntregadoHandler {
    private final ICambiarEntregadoServicePort cambiarEstadoPedido;
    private final IHttpRequestServicePort httpRequestServicePort;
    private final IUsuarioFeignClient usuarioFeignClient;
    private final ITrazaFeignClient trazaFeignClient;

    @Override
    public AsignarPedidoResponseDto cambiarEntregado(int idEmpleado, int idPedido, String pin) {
        Pedido pedido = cambiarEstadoPedido.cambiarEntregado(idEmpleado, idPedido, pin);

        AsignarPedidoResponseDto responseDto = new AsignarPedidoResponseDto();

        responseDto.setEstado(pedido.getEstado());
        responseDto.setIdPedido(pedido.getId());
        responseDto.setIdEmpleado(pedido.getIdChef());
        responseDto.setIdRestaurante(pedido.getIdRestaurante());

        CrearTrazaRequestDto trazaRequestDto = crearTrazaRequestDto(pedido);
        trazaFeignClient.crear(httpRequestServicePort.getToken(), trazaRequestDto.getIdEmpleado(),trazaRequestDto.getIdPedido(), Integer.parseInt(trazaRequestDto.getIdCliente()),
                trazaRequestDto.getCorreoEmpleado(),trazaRequestDto.getCorreoCliente(),trazaRequestDto.getNuevoEstado());

        return responseDto;
    }

    private CrearTrazaRequestDto crearTrazaRequestDto(Pedido pedido){
        UserInfoResponseDto cliente = usuarioFeignClient.getUser(httpRequestServicePort.getToken(), pedido.getIdCliente());
        UserInfoResponseDto empleado = usuarioFeignClient.getUser(httpRequestServicePort.getToken(), pedido.getIdCliente());

        CrearTrazaRequestDto requestDto = new CrearTrazaRequestDto();
        requestDto.setIdEmpleado(pedido.getIdChef());
        requestDto.setIdPedido(pedido.getId());
        requestDto.setIdCliente(String.valueOf(pedido.getIdCliente()));
        requestDto.setCorreoCliente(cliente.getEmail());
        requestDto.setCorreoEmpleado(empleado.getEmail());
        requestDto.setNuevoEstado(pedido.getEstado());

        return requestDto;
    }
}
