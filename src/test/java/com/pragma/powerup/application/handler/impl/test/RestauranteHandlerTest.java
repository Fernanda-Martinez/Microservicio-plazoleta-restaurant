package com.pragma.powerup.application.handler.impl.test;

import com.pragma.powerup.application.dto.request.CrearRestauranteRequestDto;
import com.pragma.powerup.application.dto.response.CrearPlatoResponseDto;
import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;
import com.pragma.powerup.application.handler.IRestauranteHandler;
import com.pragma.powerup.application.handler.impl.RestauranteHandler;
import com.pragma.powerup.application.mapper.IRestauranteRequestMapper;
import com.pragma.powerup.application.mapper.IRestauranteResponseMapper;
import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.model.Restaurante;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteHandlerTest {

    @Mock
    private IRestauranteServicePort restauranteServicePort;

    @Mock
    private IRestauranteRequestMapper restauranteRequestMapper;

    @InjectMocks
    private RestauranteHandler restauranteHandler;

    @Test
    void crearRestaurante() {
        // Arrange
        CrearRestauranteRequestDto requestDto = new CrearRestauranteRequestDto();
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante Test");
        restaurante.setDireccion("Carrera 22N 115");
        restaurante.setIdPropietario(2);
        restaurante.setTelefono("3006549591");
        restaurante.setUrlLogo("https://restaurante.com");
        restaurante.setNit(123456789);


        when(restauranteRequestMapper.toRestaurante(requestDto)).thenReturn(restaurante);
        when(restauranteServicePort.crear(restaurante)).thenReturn(restaurante);

        CrearRestauranteResponseDto responseDto = restauranteHandler.crearRestaurante(requestDto);

        assertEquals(123456789, responseDto.getNit());
        assertEquals("Restaurante Test", responseDto.getNombre());

    }



}









