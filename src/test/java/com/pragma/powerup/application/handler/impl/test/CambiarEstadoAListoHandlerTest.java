package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.IMensajeFeignClient;
import com.pragma.powerup.application.client.ITrazaFeignClient;
import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearTrazaRequestDto;
import com.pragma.powerup.application.dto.response.CambiarEstadoPedidoResponseDto;
import com.pragma.powerup.application.dto.response.EnviarMensajeResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.impl.CambiarEstadoPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.api.ICambiarEstadoPedidoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.infrastructure.exception.PedidoInexistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CambiarEstadoAListoHandlerTest {
    @Mock
    private ICambiarEstadoPedidoServicePort cambiarEstadoPedidoServicePort;

    @Mock
    private IAsignarPedidoServicePort asignarPedidoServicePort;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @Mock
    private IMensajeFeignClient mensajeFeignClient;

    @Mock
    private ITrazaFeignClient trazaFeignClient;

    @InjectMocks
    private CambiarEstadoPedidoHandler cambiarEstadoPedidoHandler;

    @BeforeEach
    void setUp() {
        cambiarEstadoPedidoHandler = new CambiarEstadoPedidoHandler(
                cambiarEstadoPedidoServicePort,
                asignarPedidoServicePort,
                httpRequestServicePort,
                usuarioFeignClient,
                mensajeFeignClient,
                trazaFeignClient
        );
    }

    @Test
    void cambiarEstadoPedidoEmpleadoValido() {
        // Arrange
        int idEmpleado = 1;
        int idPedido = 1;

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdCliente(1);
        pedido.setIdRestaurante(1);
        pedido.setIdChef(1);
        pedido.setEstado("Listo");

        when(asignarPedidoServicePort.buscarPedido(idPedido)).thenReturn(pedido);
        when(usuarioFeignClient.validateEmployeeRestaurant(null, 1, 1)).thenReturn(true);
        when(cambiarEstadoPedidoServicePort.cambiarEstadoPedido(eq(idEmpleado), eq(idPedido))).thenReturn(pedido);

        UserInfoResponseDto usuario = new UserInfoResponseDto();
        usuario.setNombre("John");
        usuario.setTelefono("+573022074060");
        when(usuarioFeignClient.getUser(null, 1)).thenReturn(usuario);

        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante");
        when(cambiarEstadoPedidoServicePort.obtenerRestaurante(eq(pedido.getIdRestaurante()))).thenReturn(restaurante);

        EnviarMensajeResponseDto mensajeResponseDto = new EnviarMensajeResponseDto();
        mensajeResponseDto.setEnviado(true);
        mensajeResponseDto.setPin("1234");
        when(mensajeFeignClient.enviar(anyString(), anyString(), anyString())).thenReturn(mensajeResponseDto);

        CrearTrazaRequestDto trazaRequestDto = new CrearTrazaRequestDto();
        trazaRequestDto.setIdEmpleado(pedido.getIdChef());
        trazaRequestDto.setIdPedido(pedido.getId());
        trazaRequestDto.setIdCliente(String.valueOf(pedido.getIdCliente()));
        trazaRequestDto.setCorreoCliente(usuario.getEmail());
        trazaRequestDto.setCorreoEmpleado(usuario.getEmail());
        trazaRequestDto.setNuevoEstado(pedido.getEstado());

        CambiarEstadoPedidoResponseDto responseDto = cambiarEstadoPedidoHandler.cambiarEstadoPedido(idEmpleado, idPedido);


        assertNotNull(responseDto);
        assertEquals(pedido.getId(), responseDto.getIdPedido());
        assertEquals(pedido.getIdChef(), responseDto.getIdEmpleado());
        assertEquals(pedido.getEstado(), responseDto.getEstado());
        assertEquals(pedido.getIdRestaurante(), responseDto.getIdRestaurante());
        verify(cambiarEstadoPedidoServicePort, times(1)).asignarPin(eq(pedido.getId()), eq(mensajeResponseDto.getPin()));
        verify(trazaFeignClient, times(1)).crear(null, 1, 1,
                1, null, null, "Listo");
    }

    @Test
    void cambiarEstadoPedidoEmpleadoInvalido() {

        int idEmpleado = 1;
        int idPedido = 1;
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdCliente(1);
        pedido.setIdRestaurante(1);
        pedido.setIdChef(1);
        pedido.setEstado("Nuevo");
        when(asignarPedidoServicePort.buscarPedido(idPedido)).thenReturn(pedido);
        when(usuarioFeignClient.validateEmployeeRestaurant(null, 1, 1)).thenReturn(false);

        CambiarEstadoPedidoResponseDto responseDto = cambiarEstadoPedidoHandler.cambiarEstadoPedido(idEmpleado, idPedido);

        assertNull(responseDto);
        verify(cambiarEstadoPedidoServicePort, never()).cambiarEstadoPedido(anyInt(), anyInt());
        verify(usuarioFeignClient, never()).getUser(anyString(), anyInt());
        verify(cambiarEstadoPedidoServicePort, never()).obtenerRestaurante(anyInt());
        verify(mensajeFeignClient, never()).enviar(anyString(), anyString(), anyString());
        verify(cambiarEstadoPedidoServicePort, never()).asignarPin(anyInt(), anyString());
        verify(trazaFeignClient, never()).crear(anyString(), anyInt(), anyInt(), anyInt(), anyString(), anyString(), anyString());
    }

    @Test
    void cambiarEstadoPedidoInvalido() {

        int idEmpleado = 1;
        int idPedido = 1;
        when(asignarPedidoServicePort.buscarPedido(idPedido)).thenReturn(null);

        assertThrows(PedidoInexistente.class, () -> cambiarEstadoPedidoHandler.cambiarEstadoPedido(idEmpleado, idPedido));

        verify(asignarPedidoServicePort).buscarPedido(idPedido);
        verifyNoMoreInteractions(asignarPedidoServicePort, cambiarEstadoPedidoServicePort, httpRequestServicePort, usuarioFeignClient, mensajeFeignClient, trazaFeignClient);
    }


}
