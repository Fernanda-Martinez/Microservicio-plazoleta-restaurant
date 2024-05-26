package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearTrazaRequestDto;
import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.IAsignarPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class AsignarPedidoHandler implements IAsignarPedidoHandler {
    private final IAsignarPedidoServicePort asignarPedidoServicePort;
    private final IHttpRequestServicePort httpRequestServicePort;
    private final IUsuarioFeignClient usuarioFeignClient;
    private final ITrazaFeignClient trazaFeignClient;

    @Override
    public Page<AsignarPedidoResponseDto> asignar(int idEmpleado, int idPedido, String estado,PageRequest pageRequest) {
        Pedido pedidoRes = asignarPedidoServicePort.buscarPedido(idPedido);

        if(usuarioFeignClient.validateEmployeeRestaurant(
                httpRequestServicePort.getToken(),
                idEmpleado,
                pedidoRes.getIdRestaurante()
        )){
            Page<Pedido> response = asignarPedidoServicePort.asignar(idEmpleado, idPedido, estado,pageRequest);

            Optional<Pedido> pedido = StreamSupport.stream(response.spliterator(), false)
                    .filter(item -> item.getId() == idPedido).findFirst();

            CrearTrazaRequestDto trazaRequestDto = crearTrazaRequestDto(pedido.get());
            trazaFeignClient.crear(httpRequestServicePort.getToken(), trazaRequestDto.getIdEmpleado(),trazaRequestDto.getIdPedido(), Integer.parseInt(trazaRequestDto.getIdCliente()),
                    trazaRequestDto.getCorreoEmpleado(),trazaRequestDto.getCorreoCliente(),trazaRequestDto.getNuevoEstado());
            return response.map(this::toDto);
        }

        return null;
    }

    private AsignarPedidoResponseDto toDto(Pedido pedido){
        AsignarPedidoResponseDto res = new AsignarPedidoResponseDto();
        res.setIdEmpleado(pedido.getIdChef());
        res.setIdPedido(pedido.getId());
        res.setIdRestaurante(pedido.getIdRestaurante());
        res.setEstado(pedido.getEstado());

        return res;
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
