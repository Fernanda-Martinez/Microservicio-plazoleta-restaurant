package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.application.dto.response.RealizarPedidoResponseDto;
import com.pragma.powerup.application.handler.impl.PedidoHandler;
import com.pragma.powerup.application.mapper.IPedidoRequestMapper;
import com.pragma.powerup.application.mapper.IPedidoResponseMapper;
import com.pragma.powerup.domain.api.IPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.infrastructure.exception.ExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest

class PedidoHandlerTest {

    @Mock
    private IPedidoServicePort pedidoServicePort;

    @Mock
    private IPedidoResponseMapper pedidoResponseMapper;

    private PedidoHandler pedidoHandler;

    @BeforeEach
    public void setUp() {
        pedidoServicePort = mock(IPedidoServicePort.class);
        pedidoHandler = new PedidoHandler(pedidoServicePort);
    }

    //Se crea el objeto RealizarPedidoRequestDto y Pedido, se simula el metodo registrar

    @Test
    void testRegistrar() throws ExceptionMessage {
        // Dado
        RealizarPedidoRequestDto request = new RealizarPedidoRequestDto();
        request.setIdCliente(1);
        request.setIdRestaurante(2);
        List<PlatoRequestDto> platoRequestDtoList = new ArrayList<>();

        PlatoRequestDto plato1 = new PlatoRequestDto();
        plato1.setIdPlato(1);
        plato1.setCantidad(2);
        platoRequestDtoList.add(plato1);

        PlatoRequestDto plato2 = new PlatoRequestDto();
        plato2.setIdPlato(2);
        plato2.setCantidad(1);
        platoRequestDtoList.add(plato2);
        request.setPlatoRequestDtoList(platoRequestDtoList);

        Pedido pedido = new Pedido();
        pedido.setId(3);
        pedido.setIdCliente(1);
        pedido.setIdRestaurante(2);
        pedido.setEstado("Pendiente");
        pedido.setFecha(new Date());
        pedido.setPlatoRequestDtoList(platoRequestDtoList);
        when(pedidoServicePort.registrar(any(Pedido.class))).thenReturn(pedido);
        RealizarPedidoResponseDto responseDto = new RealizarPedidoResponseDto();
        responseDto.setIdPedido(3);
        responseDto.setIdCliente(1);
        responseDto.setIdRestautante(2);
        responseDto.setEstado("Pendiente");
        when(pedidoResponseMapper.toDto(any(Pedido.class))).thenReturn(responseDto);


        RealizarPedidoResponseDto response = pedidoHandler.registrar(request);


        assertEquals(3, response.getIdPedido());
        assertEquals(1, response.getIdCliente());
        assertEquals(2, response.getIdRestautante());
        assertEquals("Pendiente", response.getEstado());
    }

}

