package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IObjectServicePort;
import com.pragma.powerup.domain.api.IRestauranteServicePort;
import com.pragma.powerup.domain.spi.IObjectPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.domain.usecase.ObjectUseCase;
import com.pragma.powerup.domain.usecase.RestauranteUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.ObjectJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IObjectRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;

    @Bean
    public IObjectPersistencePort objectPersistencePort() {
        return new ObjectJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IObjectServicePort objectServicePort() {
        return new ObjectUseCase(objectPersistencePort());
    }

    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(restaurantRepository, restauranteEntityMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {

        return new RestauranteUseCase(restaurantePersistencePort());
    }
}