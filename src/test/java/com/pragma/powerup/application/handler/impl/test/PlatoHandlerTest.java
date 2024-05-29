package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearPlatoRequestDto;
import com.pragma.powerup.application.dto.request.ModificarPlatoRequestDto;
import com.pragma.powerup.application.dto.response.CambiarEstadoPlatoResponseDto;
import com.pragma.powerup.application.dto.response.CrearPlatoResponseDto;
import com.pragma.powerup.application.dto.response.ModificarPlatoResponseDto;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    @Mock
    private CrearPlatoRequestDto crearPlatoRequestDto;

    private Plato plato;

    @InjectMocks
    private PlatoEstadoHandler platoEstadoHandler;

    @InjectMocks
    private PlatoHandler platoHandler;

    @InjectMocks
    private PlatoModifHandler platoModifHandler;

    @BeforeEach
    void setUp() {
        platoHandler = new PlatoHandler(
                platoServicePort,
                platoRequestMapper,
                platoResponseMapper,
                usuarioFeignClient,
                httpRequestServicePort
        );
        crearPlatoRequestDto = new CrearPlatoRequestDto();
        crearPlatoRequestDto.setIdPropietario(2);
        plato = new Plato();
    }

    @Test
    void crearPlato_UsuarioEsPropietario() {

        crearPlatoRequestDto.setNombre("Plato1");
        crearPlatoRequestDto.setDescripcion("Plato a la plancha");
        crearPlatoRequestDto.setPrecio(10500);
        crearPlatoRequestDto.setIdPropietario(2);
        crearPlatoRequestDto.setIdRestaurante(1);
        crearPlatoRequestDto.setIdCategoria(2);
        crearPlatoRequestDto.setUrlImagen("htto://plato.jpg");

        plato.setNombre("Plato1");
        plato.setIdCategoria(2);
        plato.setDescripcion("Plato a la plancha");
        plato.setPrecio(10500);
        plato.setIdRestaurante(1);
        plato.setUrlImagen("htto://plato.jpg");
        plato.setActivo(true);

        when(httpRequestServicePort.getToken()).thenReturn("");
        when(usuarioFeignClient.validateOwnerRole("", 2)).thenReturn(true);
        when(platoRequestMapper.toPlato(crearPlatoRequestDto)).thenReturn(plato);
        when(platoServicePort.crear(plato,crearPlatoRequestDto.getIdPropietario())).thenReturn(plato);

        CrearPlatoResponseDto platoResponseDto = new CrearPlatoResponseDto();
        platoResponseDto.setNombre("Plato1");
        platoResponseDto.setIdCategoria(2);
        platoResponseDto.setDescripcion("Plato a la plancha");
        platoResponseDto.setPrecio(10500);
        platoResponseDto.setIdRestaurante(1);
        platoResponseDto.setUrlImagen("htto://plato.jpg");
        platoResponseDto.setActivo(true);

        when(platoResponseMapper.toResponse(plato)).thenReturn(platoResponseDto);

        CrearPlatoResponseDto responseDto = platoHandler.crearPlato(crearPlatoRequestDto);



        verify(platoServicePort, times(1)).crear(plato,2);
        assertEquals(plato.getNombre(),responseDto.getNombre());
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

        ModificarPlatoResponseDto modificarPlatoResponseDto = new ModificarPlatoResponseDto();
        modificarPlatoResponseDto.setNombre("Sopa");

        when(platoModifResponseMapper.toResponse(plato)).thenReturn(modificarPlatoResponseDto);

        ModificarPlatoResponseDto responseDto = platoModifHandler.modificarPlato(modificarPlatoRequestDto);

        assertNotNull(responseDto); // Verifica que la respuesta no sea nula
        verify(platoModServicePort, times(1)).modificar(plato, 1);
        assertEquals("Sopa", responseDto.getNombre());
    }

    //Habilitar y deshabilitar plato
    @Test
    void testCambiarEstadoPlato() {

        Plato plato = new Plato();
        plato.setId(1);
        plato.setActivo(true);

        CambiarEstadoPlatoResponseDto expectedResponse = new CambiarEstadoPlatoResponseDto();
        expectedResponse.setActivo(true);

        when(habilitarDeshabilitarPlatoServicePort.cambiarEstado(1,1)).thenReturn(plato);
        when(platoCambiarEstadoResponseMapper.toResponse(plato)).thenReturn(expectedResponse);

        CambiarEstadoPlatoResponseDto response = platoEstadoHandler.cambiarEstadoPlato(1, 1);
        assertEquals(expectedResponse, response);
     }
}




