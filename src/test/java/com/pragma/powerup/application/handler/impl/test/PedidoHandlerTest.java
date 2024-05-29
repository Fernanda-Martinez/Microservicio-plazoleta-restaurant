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
import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.infrastructure.exception.ExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

 class PedidoHandlerTest {

    @Mock
    private IPedidoServicePort pedidoServicePort;

    @Mock
    private ITrazaFeignClient trazaFeignClient;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;
    @Mock
    private IRestauranteServicePort restauranteServicePort;

@InjectMocks
    private PedidoHandler pedidoHandler;
@Mock
    private UserInfoResponseDto userInfoResponseDto;
    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        pedido.setIdRestaurante(2);
        pedido.setEstado("Pendiente");
        pedido.setFecha(new Date());
        pedido.setPlatoRequestDtoList(platoRequestDtoList);
        when(pedidoServicePort.registrar(any(Pedido.class))).thenReturn(pedido);

        UserInfoResponseDto cliente = new UserInfoResponseDto();
        cliente.setEmail(" ");
        when(usuarioFeignClient.getUser(anyString(), anyInt())).thenReturn(cliente);

        CrearTrazaRequestDto trazaRequestDto = new CrearTrazaRequestDto();
        trazaRequestDto.setIdEmpleado(1);
        trazaRequestDto.setIdPedido(1);
        trazaRequestDto.setIdCliente("1");
        trazaRequestDto.setCorreoEmpleado("");
        trazaRequestDto.setCorreoCliente(cliente.getEmail());
        trazaRequestDto.setNuevoEstado(pedido.getEstado());

        when(trazaFeignClient.crear(anyString(), anyInt(), anyInt(), anyInt(), anyString(), anyString(), anyString())).thenReturn(new CrearTrazaResponseDto());

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