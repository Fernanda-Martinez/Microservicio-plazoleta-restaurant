package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.response.ListarPedidoResponseDto;
import com.pragma.powerup.application.handler.impl.ListarPedidoHandler;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IListarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarPedidoHandlerTest {
    @Mock
    private IHttpRequestServicePort httpRequestServicePort;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @Mock
    private IListarPedidoServicePort listarPedidoServicePort;

    private ListarPedidoHandler listarPedidoHandler;

    @BeforeEach
    public void setUp() {
        listarPedidoServicePort = mock(IListarPedidoServicePort.class);
        listarPedidoHandler = new ListarPedidoHandler(
                listarPedidoServicePort,
                httpRequestServicePort,
                usuarioFeignClient);
    }

    @Test
    void testListarPedidos() {

        int idEmpleado = 1;
        int idRestaurante = 1;
        when(httpRequestServicePort.getToken()).thenReturn("");
        when(usuarioFeignClient.validateEmployeeRestaurant(anyString(), idEmpleado, idRestaurante)).thenReturn(true);

        String estado = "PENDIENTE";
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(1, 1, 1, "PENDIENTE", null, 1, null));
        pedidos.add(new Pedido(2, 1, 1, "PENDIENTE", null, 1, null));
        Page<Pedido> pagePedidos = new PageImpl<>(pedidos);


         when(listarPedidoServicePort.listarPedidos(idEmpleado, idRestaurante, estado, pageRequest)).thenReturn(pagePedidos);

        Page<ListarPedidoResponseDto> response = listarPedidoHandler.listarPedidos(idEmpleado, idRestaurante, estado, pageRequest);

        assertEquals(2, response.getContent().size());
        assertEquals(1, response.getContent().get(0).getId());
        assertEquals(1, response.getContent().get(0).getIdRestaurante());
        assertEquals("PENDIENTE", response.getContent().get(0).getEstado());
        assertEquals(2, response.getContent().get(1).getId());
        assertEquals(1, response.getContent().get(1).getIdRestaurante());
        assertEquals("PENDIENTE", response.getContent().get(1).getEstado());

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
