package com.pragma.powerup.application.handler.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.pragma.powerup.application.dto.response.ListarPlatosResponseDto;
import com.pragma.powerup.application.handler.impl.ListarPlatoHandler;
import com.pragma.powerup.domain.api.IListarPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;

@ExtendWith(MockitoExtension.class)
class ListarPlatoHandlerTest {

    @Mock
    private IListarPlatoServicePort listarPlatoServicePort;

    @InjectMocks
    private ListarPlatoHandler listarPlatoHandler;

    //Se simula la interfaz IListarPlatoServicePort.
    // Luego, se crea una instancia de ListarPlatoHandler y le pasamos la dependencia simulada
    @BeforeEach
    void setUp() {
        listarPlatoServicePort = mock(IListarPlatoServicePort.class);
        listarPlatoHandler = new ListarPlatoHandler(listarPlatoServicePort);
    }

    //Se configura el objeto simulado IListarPlatoServicePort para que devuelva una página de platos paginados y filtrados por categoría.
    // Luego, se verifica que el método listarPlatos devuelve una página de ListarPlatosResponseDto con los valores correctos para cada plato.
    @Test
    void testListarPlatos() {
        // Arrange
        int pageSize = 10;
        PageRequest params = PageRequest.of(0, pageSize);
        int idCategoria = 1;
        int idRestaurante = 1;
        List<Plato> platos = new ArrayList<>();
        Plato plato1 = new Plato();
        plato1.setNombre("Plato A");
        plato1.setUrlImagen("http://test.com/imagenA.png");
        platos.add(plato1);
        Plato plato2 = new Plato();
        plato2.setNombre("Plato B");
        plato2.setUrlImagen("http://test.com/imagenB.png");
        platos.add(plato2);
        Page<Plato> page = new PageImpl<>(platos);
        when(listarPlatoServicePort.listarPlato(params, idCategoria, idRestaurante)).thenReturn(page);

        // Act
        Page<ListarPlatosResponseDto> result = listarPlatoHandler.listarPlatos(params, idCategoria, idRestaurante);

        // Assert
        assertEquals(2, result.getTotalElements());
        ListarPlatosResponseDto dto1 = result.getContent().get(0);
        assertEquals("Plato A", dto1.getNombre());
        assertEquals("http://test.com/imagenA.png", dto1.getUrlImagen());
        ListarPlatosResponseDto dto2 = result.getContent().get(1);
        assertEquals("Plato B", dto2.getNombre());
        assertEquals("http://test.com/imagenB.png", dto2.getUrlImagen());
    }

    //Se verifica que el metodo devuelva un objeto ListarPlatosResponseDto con los valores correctos para un plato dado.
    @Test
    void testToPlatoResponse() {
        // Arrange
        Plato plato = new Plato();
        plato.setNombre("Plato Test");
        plato.setUrlImagen("http://test.com/imagen.png");

        // Act
        ListarPlatosResponseDto result = listarPlatoHandler.toPlatoResponse(plato);

        // Assert
        assertEquals("Plato Test", result.getNombre());
        assertEquals("http://test.com/imagen.png", result.getUrlImagen());
    }
}
