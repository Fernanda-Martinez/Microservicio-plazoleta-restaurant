package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.spi.IListarRestaurantePersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort, IListarRestaurantePersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    @Override
    public Restaurante crear(Restaurante restaurante) {
        RestauranteEntity nuevoRestaurante = restaurantRepository.save(restauranteEntityMapper.toEntity(restaurante));
        return restauranteEntityMapper.toRestauranteModel(nuevoRestaurante);
    }

    @Override
    public Page<Restaurante> listarRestaurante(PageRequest pageRequest) {
        Page<RestauranteEntity> listarRestaurante = restaurantRepository.findAll(pageRequest);
        return listarRestaurante.map(this::toRestauranteModel);

    }
    private Restaurante toRestauranteModel(RestauranteEntity restauranteEntity) {
        return new Restaurante(
                restauranteEntity.getId(),
                restauranteEntity.getNombre(),
                restauranteEntity.getDireccion(),
                restauranteEntity.getIdPropietario(),
                restauranteEntity.getTelefono(),
                restauranteEntity.getUrlLogo(),
                restauranteEntity.getNit()
                );
    }
}
