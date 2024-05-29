package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.response.CancelarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.impl.CancelarPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.api.ICancelarPedidoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelarPedidoHandlerTest {
    @Mock
    private ICancelarPedidoServicePort cancelarPedidoServicePort;

    @Mock
    private IAsignarPedidoServicePort asignarPedidoServicePort;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @Mock
    private ITrazaFeignClient trazaFeignClient;

    @InjectMocks
    private CancelarPedidoHandler cancelarPedidoHandler;

    @BeforeEach
    void setUp() {

        cancelarPedidoHandler = new CancelarPedidoHandler(
                cancelarPedidoServicePort,
                asignarPedidoServicePort,
                httpRequestServicePort,
                usuarioFeignClient,
                trazaFeignClient
        );
    }
    @Test
    void cancelarPedido() {
        int idEmpleado = 1;
        int idPedido = 2;
        String message = "Pedido cancelado";
        String token = "token";


        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdCliente(3);
        pedido.setIdChef(4);
        pedido.setEstado("En proceso");

        when(cancelarPedidoServicePort.cancelarPedido(eq(idEmpleado), eq(idPedido))).thenReturn(message);
        when(asignarPedidoServicePort.buscarPedido(eq(idPedido))).thenReturn(pedido);
        when(httpRequestServicePort.getToken()).thenReturn(token);
        when(usuarioFeignClient.getUser(eq(token), eq(pedido.getIdCliente()))).thenReturn(new UserInfoResponseDto());
        when(trazaFeignClient.crear(
                "token",4,2,3,null,null,"En proceso"
        )).thenReturn(null);

        CancelarPedidoResponseDto response = cancelarPedidoHandler.cancelarPedido(idEmpleado, idPedido);

        assertEquals(message, response.getMessage());
    }

}
