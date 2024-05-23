package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import com.pragma.powerup.application.handler.impl.AsignarPedidoHandler;
import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsignarPedidoHandlerTest {

    @Mock
    private IAsignarPedidoServicePort asignarPedidoServicePort;

    private AsignarPedidoHandler asignarPedidoHandler;

    //Se simula la interfaz y se crea una instancia con la interfaz simulada
    @BeforeEach
    void setUp() {
        asignarPedidoServicePort = mock(IAsignarPedidoServicePort.class);
        asignarPedidoHandler = new AsignarPedidoHandler(asignarPedidoServicePort);
    }
//Se configuea la  simulación para que devuelva un objeto Pedido y verificamos que el
// método devuelve un objeto AsignarPedidoResponseDto con los valores esperados.

    @Test
    void testAsignar() {

        int idEmpleado = 1;
        int idPedido = 2;
        Pedido pedido = createPedido();
        when(asignarPedidoServicePort.asignar(idEmpleado, idPedido)).thenReturn(pedido);


        AsignarPedidoResponseDto response = asignarPedidoHandler.asignar(idEmpleado, idPedido);


        assertEquals(idEmpleado, response.getIdEmpleado());
        assertEquals(idPedido, response.getIdPedido());
        assertEquals(2, response.getIdRestaurante());
        assertEquals("ASIGNADO", response.getEstado());
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
