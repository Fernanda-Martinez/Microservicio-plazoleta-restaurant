package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IMensajeFeignClient;
import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearTrazaRequestDto;
import com.pragma.powerup.application.dto.request.MensajeRequestDto;
import com.pragma.powerup.application.dto.response.CambiarEstadoPedidoResponseDto;
import com.pragma.powerup.application.dto.response.EnviarMensajeResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.ICambiarEstadoPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.api.ICambiarEstadoPedidoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.infrastructure.exception.PedidoInexistente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CambiarEstadoPedidoHandler implements ICambiarEstadoPedidoHandler {

    private final ICambiarEstadoPedidoServicePort cambiarEstadoPedidoServicePort;
    private final IAsignarPedidoServicePort asignarPedidoServicePort;
    private final IHttpRequestServicePort httpRequestServicePort;
    private final IUsuarioFeignClient usuarioFeignClient;
    private final IMensajeFeignClient mensajeFeignClient;
    private final ITrazaFeignClient trazaFeignClient;

    @Override
    public CambiarEstadoPedidoResponseDto cambiarEstadoPedido(int idEmpleado, int idPedido) {

        Pedido pedido = asignarPedidoServicePort.buscarPedido(idPedido);
        if (pedido == null) {
            throw new PedidoInexistente("El pedido no existe");
        }
        if (usuarioFeignClient.validateEmployeeRestaurant(
                httpRequestServicePort.getToken(),
                idEmpleado,
                pedido.getIdRestaurante()
        )) {
            Pedido pedidoActualizado = cambiarEstadoPedidoServicePort.cambiarEstadoPedido(idEmpleado, idPedido);

            MensajeRequestDto requestDto = new MensajeRequestDto();
            UserInfoResponseDto usuario = usuarioFeignClient.getUser(httpRequestServicePort.getToken(), pedidoActualizado.getIdCliente());

            Restaurante restaurante = cambiarEstadoPedidoServicePort.obtenerRestaurante(pedidoActualizado.getIdRestaurante());


            requestDto.setNombre(usuario.getNombre());
            requestDto.setTelefono(usuario.getTelefono());
            requestDto.setRestaurante(restaurante.getNombre());

            EnviarMensajeResponseDto responseMensaje =  mensajeFeignClient.enviar(requestDto.getNombre(), requestDto.getTelefono(), requestDto.getRestaurante());

            if(responseMensaje.isEnviado()){
                cambiarEstadoPedidoServicePort.asignarPin(pedidoActualizado.getId(), responseMensaje.getPin());
            }

            CambiarEstadoPedidoResponseDto response = new CambiarEstadoPedidoResponseDto();

            response.setIdPedido(pedidoActualizado.getId());
            response.setIdEmpleado(pedidoActualizado.getIdChef());
            response.setEstado(pedidoActualizado.getEstado());
            response.setIdRestaurante(pedidoActualizado.getIdRestaurante());



            CrearTrazaRequestDto trazaRequestDto = crearTrazaRequestDto(pedidoActualizado);
            trazaFeignClient.crear(httpRequestServicePort.getToken(), trazaRequestDto.getIdEmpleado(),trazaRequestDto.getIdPedido(), Integer.parseInt(trazaRequestDto.getIdCliente()),
                    trazaRequestDto.getCorreoEmpleado(),trazaRequestDto.getCorreoCliente(),trazaRequestDto.getNuevoEstado());
            return response;
        }

        return null;
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
