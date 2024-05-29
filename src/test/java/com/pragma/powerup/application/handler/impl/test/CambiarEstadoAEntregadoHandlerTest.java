package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.impl.CambiarEntregadoHandler;
import com.pragma.powerup.domain.api.ICambiarEntregadoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CambiarEstadoAEntregadoHandlerTest {
    @Mock
    private ICambiarEntregadoServicePort cambiarEntregadoServicePort;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @Mock
    private ITrazaFeignClient trazaFeignClient;

    @InjectMocks
    private CambiarEntregadoHandler cambiarEntregadoHandler;

    @BeforeEach
    void setUp() {
        cambiarEntregadoHandler = new CambiarEntregadoHandler(
                cambiarEntregadoServicePort,
                httpRequestServicePort,
                usuarioFeignClient,
                trazaFeignClient
        );
    }


    @Test
    void cambiarEntregado() {
        int idEmpleado = 1;
        int idPedido = 1;
        String pin = "1234";

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdChef(idEmpleado);
        pedido.setIdRestaurante(1);
        pedido.setEstado("ENTREGADO");

        when(cambiarEntregadoServicePort.cambiarEntregado(anyInt(), anyInt(), anyString())).thenReturn(pedido);

        UserInfoResponseDto cliente = new UserInfoResponseDto();
        cliente.setEmail("cliente@test.com");
        when(usuarioFeignClient.getUser(null, 0)).thenReturn(cliente);

        AsignarPedidoResponseDto responseDto = cambiarEntregadoHandler.cambiarEntregado(idEmpleado, idPedido, pin);

        assertEquals(pedido.getEstado(), responseDto.getEstado());
        assertEquals(pedido.getId(), responseDto.getIdPedido());
        assertEquals(pedido.getIdChef(), responseDto.getIdEmpleado());
        assertEquals(pedido.getIdRestaurante(), responseDto.getIdRestaurante());
    }
}
