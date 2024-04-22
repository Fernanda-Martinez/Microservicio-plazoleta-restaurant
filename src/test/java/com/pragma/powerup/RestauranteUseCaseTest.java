package com.pragma.powerup;

import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.domain.usecase.RestauranteUseCase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestauranteUseCaseTest {

    private IRestaurantePersistencePort restaurantePersistencePort = mock(IRestaurantePersistencePort.class);
    private RestauranteUseCase restauranteUseCase = new RestauranteUseCase(restaurantePersistencePort);

    @Test
    public void testCrear() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Prueba1");
        restaurante.setDireccion("prueba1");
        restaurante.setIdPropietario(1);
        restaurante.setTelefono("3022074060");
        restaurante.setUrlLogo("???");
        restaurante.setNit(1);

        // Mock the behavior of the persistence port
        when(restaurantePersistencePort.crear(restaurante)).thenReturn(restaurante);

        // Act
        Restaurante createdRestaurante = restauranteUseCase.crear(restaurante);

        // Assert
        assertEquals(restaurante, createdRestaurante);
        verify(restaurantePersistencePort, times(1)).crear(restaurante);
    }
}