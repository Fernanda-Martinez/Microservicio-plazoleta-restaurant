package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.impl.AsignarPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsignarPedidoHandlerTest {

    @Mock
    private IAsignarPedidoServicePort asignarPedidoServicePort;
    @Mock
    private  IHttpRequestServicePort httpRequestServicePort;
    @Mock
    private  IUsuarioFeignClient usuarioFeignClient;
    private  ITrazaFeignClient trazaFeignClient;

    @Mock
    private AsignarPedidoHandler asignarPedidoHandler;

    //Se simula la interfaz y se crea una instancia con la interfaz simulada
    @BeforeEach
    void setUp() {
        asignarPedidoServicePort = mock(IAsignarPedidoServicePort.class);
        asignarPedidoHandler = new AsignarPedidoHandler(asignarPedidoServicePort,httpRequestServicePort,usuarioFeignClient,trazaFeignClient);
    }
//Se configuea la  simulación para que devuelva un objeto Pedido y verificamos que el
// método devuelve un objeto AsignarPedidoResponseDto con los valores esperados.

    @Test
    void testAsignar() {

        int idEmpleado = 1;
        int idPedido = 2;
        Pedido pedido = createPedido();
        String estado = "Asignado";
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Pedido> pedidoList = new ArrayList<>();
        pedidoList.add(pedido);
        Page<Pedido> pedidoPage = new PageImpl<>(pedidoList);

        when(usuarioFeignClient.validateEmployeeRestaurant(any(), anyInt(), anyInt())).thenReturn(true);
        when(asignarPedidoServicePort.asignar(idEmpleado, idPedido,estado,pageRequest)).thenReturn(pedidoPage);
        when(asignarPedidoServicePort.buscarPedido(2)).thenReturn(pedido);
        when(usuarioFeignClient.getUser(" ",1)).thenReturn(new UserInfoResponseDto());

        Page<AsignarPedidoResponseDto> response = asignarPedidoHandler.asignar(idEmpleado, idPedido, estado, pageRequest);


        assertEquals(idEmpleado, response.getContent().get(0).getIdEmpleado());
        assertEquals(idPedido, response.getContent().get(0).getIdPedido());
        assertEquals(2, response.getContent().get(0).getIdRestaurante());
        assertEquals("ASIGNADO", response.getContent().get(0).getEstado());
    }
    // Se crea un método auxiliar para crear un objeto Pedido para fines de prueba
    private Pedido createPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(2);
        pedido.setIdRestaurante(2);
        pedido.setEstado("ASIGNADO");
        pedido.setIdChef(1);
        return pedido;
    }
}
