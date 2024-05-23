package com.pragma.powerup.application.handler.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.pragma.powerup.application.dto.response.ListarRestauranteResponseDto;
import com.pragma.powerup.application.handler.impl.ListarRestauranteHandler;
import com.pragma.powerup.domain.api.IListarRestauranteServicePort;
import com.pragma.powerup.domain.model.Restaurante;

class ListarRestauranteHandlerTest {

   @Mock
   private IListarRestauranteServicePort listarRestauranteServicePort;

   @InjectMocks
   private ListarRestauranteHandler listarRestauranteHandler;

   //se simula la interfaz IListarRestauranteServicePort. Luego, se crea
   // una instancia de ListarRestauranteHandler y le pasamos la dependencia simulada.
   @BeforeEach
   void setUp() {
      listarRestauranteServicePort = mock(IListarRestauranteServicePort.class);
      listarRestauranteHandler = new ListarRestauranteHandler(listarRestauranteServicePort);
   }

   //se verifica que el método listarRestaurante del Handler devuelve una página
   // de ListarRestauranteResponseDto con los valores correctos para cada restaurante.
   @Test
   void testListarRestaurante() {
      // Arrange
      int pageSize = 10;
      PageRequest params = PageRequest.of(0, pageSize);
      List<Restaurante> restaurantes = new ArrayList<>();
      Restaurante restaurante1 = new Restaurante();
      restaurante1.setNombre("Restaurante A");
      restaurante1.setUrlLogo("http://test.com/logoA.png");
      restaurantes.add(restaurante1);
      Restaurante restaurante2 = new Restaurante();
      restaurante2.setNombre("Restaurante B");
      restaurante2.setUrlLogo("http://test.com/logoB.png");
      restaurantes.add(restaurante2);
      Page<Restaurante> page = new PageImpl<>(restaurantes);
      when(listarRestauranteServicePort.listarRestaurante(params)).thenReturn(page);


      Page<ListarRestauranteResponseDto> result = listarRestauranteHandler.listarRestaurante(params);

      // Assert
      assertEquals(2, result.getTotalElements());
      ListarRestauranteResponseDto dto1 = result.getContent().get(0);
      assertEquals("Restaurante A", dto1.getNombre());
      assertEquals("http://test.com/logoA.png", dto1.getUrlImagen());
      ListarRestauranteResponseDto dto2 = result.getContent().get(1);
      assertEquals("Restaurante B", dto2.getNombre());
      assertEquals("http://test.com/logoB.png", dto2.getUrlImagen());
   }

   //Se verifica que se devuelve un objeto ListarRestauranteResponseDto con los valores correctos para un restaurante dado.
   @Test
   void testToRestauranteResponse() {

      Restaurante restaurante = new Restaurante();
      restaurante.setNombre("Restaurante Test");
      restaurante.setUrlLogo("http://test.com/logo.png");


      ListarRestauranteResponseDto result = listarRestauranteHandler.toRestauranteResponse(restaurante);

      // Assert
      assertEquals("Restaurante Test", result.getNombre());
      assertEquals("http://test.com/logo.png", result.getUrlImagen());
   }
}

