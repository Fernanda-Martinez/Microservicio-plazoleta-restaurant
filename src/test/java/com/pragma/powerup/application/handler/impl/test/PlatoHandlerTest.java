package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearPlatoRequestDto;
import com.pragma.powerup.application.dto.request.ModificarPlatoRequestDto;
import com.pragma.powerup.application.dto.response.CambiarEstadoPlatoResponseDto;
import com.pragma.powerup.application.dto.response.CrearPlatoResponseDto;
import com.pragma.powerup.application.dto.response.ModificarPlatoResponseDto;
import com.pragma.powerup.application.handler.IPlatoHandler;
import com.pragma.powerup.application.handler.impl.PlatoEstadoHandler;
import com.pragma.powerup.application.handler.impl.PlatoHandler;
import com.pragma.powerup.application.handler.impl.PlatoModifHandler;
import com.pragma.powerup.application.mapper.*;
import com.pragma.powerup.domain.api.ICambiarEstadoPlatoServicePort;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IPlatoModServicePort;
import com.pragma.powerup.domain.api.IPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PlatoHandlerTest {

    @Mock
    private IPlatoServicePort platoServicePort;

    @Mock
    private IPlatoModServicePort platoModServicePort;

    @Mock
    private IPlatoRequestMapper platoRequestMapper;

    @Mock
    private IPlatoResponseMapper platoResponseMapper;

    @Mock
    private IPlatoModifResponseMapper platoModifResponseMapper;

    @Mock
    private IPlatoModifRequestMapper platoModifRequestMapper;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;

    @Mock
    private ICambiarEstadoPlatoServicePort habilitarDeshabilitarPlatoServicePort;

    @Mock
    private IPlatoCambiarEstadoResponseMapper platoCambiarEstadoResponseMapper;

    @InjectMocks
    private PlatoEstadoHandler platoEstadoHandler;

    @InjectMocks
    private PlatoHandler platoHandler;

    @InjectMocks
    private PlatoModifHandler platoModifHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPlato_UsuarioEsPropietario() {
        CrearPlatoRequestDto platoRequestDto = new CrearPlatoRequestDto();
        platoRequestDto.setIdPropietario(1);

        when(httpRequestServicePort.getToken()).thenReturn(" token_propietario");
        when(usuarioFeignClient.validateOwnerRole(anyString(), anyInt())).thenReturn(true);

        Plato plato = new Plato();
        when(platoRequestMapper.toPlato(platoRequestDto)).thenReturn(plato);
        plato.setNombre("Carne");
        plato.setDescripcion("Descripción");
        plato.setPrecio(10600);

        when(platoResponseMapper.toResponse(plato)).thenReturn(new CrearPlatoResponseDto());

        CrearPlatoResponseDto responseDto = platoHandler.crearPlato(platoRequestDto);

        assertNotNull(responseDto);
        verify(platoServicePort, times(1)).crear(plato, 1);
    }


    @Test
    void crearPlato_UsuarioNoEsPropietario() {
        CrearPlatoRequestDto platoRequestDto = new CrearPlatoRequestDto();
        platoRequestDto.setIdPropietario(1);

        when(httpRequestServicePort.getToken()).thenReturn("token_NOpropietario");
        when(usuarioFeignClient.validateOwnerRole(anyString(), anyInt())).thenReturn(false);

        CrearPlatoResponseDto responseDto = platoHandler.crearPlato(platoRequestDto);

        assertNull(responseDto);
        verify(platoServicePort, never()).crear(any(), anyInt());
    }

    //Modificar Plato
    @Test
    void modificarPlato_UsuarioEsPropietario() {
        ModificarPlatoRequestDto modificarPlatoRequestDto = new ModificarPlatoRequestDto();
        modificarPlatoRequestDto.setIdPropietario(1);

        when(httpRequestServicePort.getToken()).thenReturn("token_propietario");
        when(usuarioFeignClient.validateOwnerRole(anyString(), anyInt())).thenReturn(true);

        Plato plato = new Plato();
        plato.setNombre("Carne");
        when(platoModifRequestMapper.toPlatoModif(modificarPlatoRequestDto)).thenReturn(plato);
        plato.setNombre("Sopa");

        when(platoModServicePort.modificar(plato, modificarPlatoRequestDto.getIdPropietario())).thenReturn(plato);
        when(platoModifResponseMapper.toResponse(plato)).thenReturn(new ModificarPlatoResponseDto());

        ModificarPlatoResponseDto responseDto = platoModifHandler.modificarPlato(modificarPlatoRequestDto);

        assertNotNull(responseDto); // Verifica que la respuesta no sea nula
        verify(platoModServicePort, times(1)).modificar(plato, 1);
        assertEquals("Sopa", plato.getNombre());
    }

    //Habilitar y deshabilitar plato
    @Test
    void testCambiarEstadoPlato() {


        Plato plato = new Plato();
        plato.setId(1);
        plato.setActivo(true);

        CambiarEstadoPlatoResponseDto expectedResponse = new CambiarEstadoPlatoResponseDto();

        when(habilitarDeshabilitarPlatoServicePort.cambiarEstado(anyInt(), anyInt())).thenReturn(plato);
        when(platoCambiarEstadoResponseMapper.toResponse(plato)).thenReturn(expectedResponse);

        CambiarEstadoPlatoResponseDto response = platoEstadoHandler.cambiarEstadoPlato(1, 1);

        assertEquals(expectedResponse, response);
     }
}




