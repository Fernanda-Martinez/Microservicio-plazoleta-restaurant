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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private IPlatoModifRequestMapper platoModifRequestMapper;

    @Mock
    private IPlatoModifResponseMapper platoModifResponseMapper;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;


    @InjectMocks
    private PlatoHandler platoHandler;

    @InjectMocks
    private PlatoModifHandler platoModifHandler;

    private PlatoEstadoHandler platoEstadoHandler;
    private ICambiarEstadoPlatoServicePort cambiarEstadoPlatoServicePort;
    private IPlatoCambiarEstadoResponseMapper platoCambiarEstadoResponseMapper;

    @Test
    void crearPlato() {
        // Arrange
        CrearPlatoRequestDto requestDto = new CrearPlatoRequestDto();
        Plato plato = new Plato();
        plato.setNombre("Plato Test");
        plato.setPrecio(100);
        plato.setIdCategoria(1);
        plato.setDescripcion("plato test 1");
        plato.setIdRestaurante(1);
        plato.setUrlImagen("https://pizza.com");
        plato.setActivo(true);
        when(platoRequestMapper.toPlato(requestDto)).thenReturn(plato);
        when(platoServicePort.crear(plato)).thenReturn(plato);


        CrearPlatoResponseDto responseDto = new CrearPlatoResponseDto();
        responseDto.setNombre("Plato Test");
        responseDto.setPrecio(100);
        when(platoResponseMapper.toResponse(plato)).thenReturn(responseDto);

        // Act
        CrearPlatoResponseDto result = platoHandler.crearPlato(requestDto);

        // Assert
        assertEquals("Plato Test", result.getNombre());
        assertEquals(100, result.getPrecio(), 0.0);
    }

    @DisplayName("verifica que el método crearPlato devuelve el CrearplatoResponseDto esperado y " +
            "llama al platoServicePort con el objeto Plato correcto.")
    @Test
    void platoCorrecto() {
        // Arrange
        CrearPlatoRequestDto requestDto = new CrearPlatoRequestDto();
        Plato plato = new Plato();
        when(platoRequestMapper.toPlato(requestDto)).thenReturn(plato);

        // Act
        platoHandler.crearPlato(requestDto);

        // Assert
        verify(platoServicePort).crear(plato);
    }

    @Test
    void modificarPlatos() {
        // Arrange
        ModificarPlatoRequestDto requestDto = new ModificarPlatoRequestDto();
        Plato plato = new Plato();
        plato.setNombre("Plato Test");
        plato.setDescripcion("No se");
        plato.setPrecio(100);

        requestDto.setPrecio(100);
        requestDto.setIdPropietario(2);
        requestDto.setId(0);
        requestDto.setDescripcion("No se");
        when(platoModifRequestMapper.toPlatoModif(requestDto)).thenReturn(plato);
        when(platoModServicePort.modificar(plato, requestDto.getIdPropietario())).thenReturn(plato);
        ModificarPlatoResponseDto responseDto = new ModificarPlatoResponseDto();
        responseDto.setNombre("Plato Modif");
        responseDto.setPrecio(230);
        when(platoModifResponseMapper.toResponse(plato)).thenReturn(responseDto);
        when(usuarioFeignClient.validateOwnerRole("token", requestDto.getIdPropietario())).thenReturn(true);


        ModificarPlatoResponseDto result = platoModifHandler.modificarPlato(requestDto);

        assertEquals("Plato Modif", result.getNombre());
        assertEquals(230, result.getPrecio(), 0.0);
    }

    //  Se simulan las interfaces ICambiarEstadoPlatoServicePort e IPlatoCambiarEstadoResponseMapper utilizando Mockito.
    //  Luego, se crea una instancia de PlatoEstadoHandler y le pasamos las dependencias simuladas.
    @BeforeEach
    public void setUp() {
        cambiarEstadoPlatoServicePort = mock(ICambiarEstadoPlatoServicePort.class);
        platoCambiarEstadoResponseMapper = mock(IPlatoCambiarEstadoResponseMapper.class);
        platoEstadoHandler = new PlatoEstadoHandler(cambiarEstadoPlatoServicePort, platoCambiarEstadoResponseMapper);
    }

//se verifica que el método cambiarEstadoPlato llama correctamente a los métodos de los mocks cambiarEstadoPlatoServicePort y platoCambiarEstadoResponseMapper
// y devuelve el objeto CambiarEstadoPlatoResponseDto con los valores correctos.
    @Test
    void testCambiarEstadoPlato() {

        int id = 1;
        Plato plato = new Plato();
        plato.setId(id);
        //when(cambiarEstadoPlatoServicePort.cambiarEstado(id, )).thenReturn(plato);
        CambiarEstadoPlatoResponseDto responseDto = new CambiarEstadoPlatoResponseDto();
        responseDto.setIdRestaurante(id);
        when(platoCambiarEstadoResponseMapper.toResponse(plato)).thenReturn(responseDto);

        CambiarEstadoPlatoResponseDto response = platoEstadoHandler.cambiarEstadoPlato(id);

        assertEquals(id, response.getIdRestaurante());
    }
}
