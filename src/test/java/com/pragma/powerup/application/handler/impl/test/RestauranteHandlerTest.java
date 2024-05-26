package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.client.IUsuarioFeignClient;
import com.pragma.powerup.application.dto.request.CrearRestauranteRequestDto;
import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;
import com.pragma.powerup.application.handler.impl.RestauranteHandler;
import com.pragma.powerup.application.mapper.IRestauranteRequestMapper;
import com.pragma.powerup.domain.api.IHttpRequestServicePort;
import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.infrastructure.http.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteHandlerTest {
@Mock
private CrearRestauranteRequestDto restauranteRequestDto;
    @Mock
    private IRestauranteServicePort restauranteServicePort;

    private Restaurante restaurante;

    @Mock
    private IRestauranteRequestMapper restauranteRequestMapper;

    @InjectMocks
    private RestauranteHandler restauranteHandler;

    @Mock
    private IHttpRequestServicePort httpRequestServicePort;

    @Mock
    private IUsuarioFeignClient usuarioFeignClient;

    @BeforeEach
    void setUp() {
        restauranteRequestDto = new CrearRestauranteRequestDto();
        restaurante = new Restaurante();
    }
    @Test
    void crearRestaurante() {

        restauranteRequestDto.setNombre("Restaurante Test");
        restauranteRequestDto.setDireccion("Carrera 22N 115");
        restauranteRequestDto.setIdPropietario(2);
        restauranteRequestDto.setTelefono("3006549591");
        restauranteRequestDto.setUrlLogo("https://restaurante.com");
        restauranteRequestDto.setNit(123456789);

        restaurante.setNombre("Restaurante Test");
        restaurante.setDireccion("Carrera 22N 115");
        restaurante.setIdPropietario(2);
        restaurante.setTelefono("3006549591");
        restaurante.setUrlLogo("https://restaurante.com");
        restaurante.setNit(123456789);

        when(httpRequestServicePort.getToken()).thenReturn("");
        when(usuarioFeignClient.validateAdminRole(anyString(), anyInt())).thenReturn(true);
        when(restauranteRequestMapper.toRestaurante(restauranteRequestDto)).thenReturn(restaurante);
        when(restauranteServicePort.crear(restaurante)).thenReturn(restaurante);


        CrearRestauranteResponseDto responseDto = restauranteHandler.crear(restauranteRequestDto);

        verify(httpRequestServicePort, times(1)).getToken();
        verify(usuarioFeignClient, times(1)).validateAdminRole(anyString(), anyInt());
        verify(restauranteRequestMapper, times(1)).toRestaurante(restauranteRequestDto);
        verify(restauranteServicePort, times(1)).crear(restaurante);
        assertEquals(restaurante.getNit(), responseDto.getNit());

    }




}









