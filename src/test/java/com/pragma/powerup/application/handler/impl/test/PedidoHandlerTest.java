package com.pragma.powerup.application.handler.impl.test;
import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearTrazaRequestDto;
import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.response.CrearTrazaResponseDto;
import com.pragma.powerup.application.dto.response.RealizarPedidoResponseDto;
import com.pragma.powerup.application.handler.impl.PedidoHandler;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.infrastructure.exception.ExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoHandlerTest {

    @Mock
    private IPedidoServicePort pedidoServicePort;

    @Mock
    private ITrazaFeignClient trazaFeignClient;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;


@InjectMocks
    private PedidoHandler pedidoHandler;

    @BeforeEach
    public void setUp() {

        pedidoHandler = new PedidoHandler(
                pedidoServicePort,
                trazaFeignClient,
                usuarioFeignClient,
                httpRequestServicePort
        );
    }

    @Test
    void registrarPedido() throws ExceptionMessage {

        RealizarPedidoRequestDto requestDto = new RealizarPedidoRequestDto();
        requestDto.setIdCliente(1);
        requestDto.setIdRestaurante(1);
        List<PlatoRequestDto> platoRequestDtoList = new ArrayList<>();

        PlatoRequestDto plato1 = new PlatoRequestDto();
        plato1.setIdPlato(1);
        plato1.setCantidad(2);
        platoRequestDtoList.add(plato1);

        PlatoRequestDto plato2 = new PlatoRequestDto();
        plato2.setIdPlato(2);
        plato2.setCantidad(1);
        platoRequestDtoList.add(plato2);

        requestDto.setPlatoRequestDtoList(platoRequestDtoList);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setIdCliente(1);
        pedido.setIdRestaurante(1);
        pedido.setEstado("Pendiente");
        pedido.setFecha(new Date());
        pedido.setPlatoRequestDtoList(platoRequestDtoList);
        when(pedidoServicePort.registrar(any(Pedido.class))).thenReturn(pedido);

        UserInfoResponseDto cliente = new UserInfoResponseDto();
        cliente.setEmail(" ");
        when(usuarioFeignClient.getUser(null, 1)).thenReturn(cliente);

        CrearTrazaRequestDto trazaRequestDto = new CrearTrazaRequestDto();
        trazaRequestDto.setIdEmpleado(1);
        trazaRequestDto.setIdPedido(1);
        trazaRequestDto.setIdCliente("1");
        trazaRequestDto.setCorreoEmpleado("");
        trazaRequestDto.setCorreoCliente(cliente.getEmail());
        trazaRequestDto.setNuevoEstado(pedido.getEstado());

        when(trazaFeignClient.crear(null, 0, 1, 1, "", " ", "Pendiente")).thenReturn(new CrearTrazaResponseDto());

        RealizarPedidoResponseDto responseDto = new RealizarPedidoResponseDto();
        responseDto.setIdPedido(pedido.getId());
        responseDto.setIdCliente(requestDto.getIdCliente());
        responseDto.setIdRestautante(requestDto.getIdRestaurante());
        responseDto.setEstado(pedido.getEstado());


        RealizarPedidoResponseDto result = pedidoHandler.registrar(requestDto);

        // Assert
        assertEquals(responseDto, result);
    }


}