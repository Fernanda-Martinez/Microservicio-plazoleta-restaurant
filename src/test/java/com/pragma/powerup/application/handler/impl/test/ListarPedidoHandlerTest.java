package com.pragma.powerup.application.handler.impl.test;


import com.pragma.powerup.application.dto.response.ListarPedidoResponseDto;
import com.pragma.powerup.application.handler.impl.ListarPedidoHandler;
import com.pragma.powerup.domain.api.IListarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListarPedidoHandlerTest {

    @Mock
    private IListarPedidoServicePort listarPedidoServicePort;

    private ListarPedidoHandler listarPedidoHandler;

    @BeforeEach
    public void setUp() {
        listarPedidoServicePort = mock(IListarPedidoServicePort.class);
        listarPedidoHandler = new ListarPedidoHandler(listarPedidoServicePort);
    }

    @Test
    void testListarPedidos() {

        int idEmpleado = 1;
        int idRestaurante = 2;
        String estado = "PENDIENTE";
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Pedido> pedidos = createPedidosPage();

        when(listarPedidoServicePort.listarPedidos(idEmpleado, idRestaurante, estado, pageRequest))
                .thenReturn(pedidos);

        Page<ListarPedidoResponseDto> result = listarPedidoHandler.listarPedidos(idEmpleado, idRestaurante, estado, pageRequest);


        assertEquals(pedidos.getTotalElements(), result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(pedidos.getContent().get(0).getId(), result.getContent().get(0).getId());
        assertEquals(pedidos.getContent().get(0).getEstado(), result.getContent().get(0).getEstado());
        assertEquals(pedidos.getContent().get(0).getIdRestaurante(), result.getContent().get(0).getIdRestaurante());
        assertEquals(pedidos.getContent().get(0).getFecha(), result.getContent().get(0).getFecha());
    }

    private Page<Pedido> createPedidosPage() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setEstado("PENDIENTE");
        pedido.setIdRestaurante(2);
        pedido.setFecha(new Date());

        List<Pedido> pedidosList = new ArrayList<>();
        pedidosList.add(pedido);

        return new PageImpl<>(pedidosList);

    }
}
