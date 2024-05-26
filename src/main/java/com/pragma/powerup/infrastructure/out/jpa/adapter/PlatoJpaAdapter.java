package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.ICambiarEstadoPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IListarPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IPlatoModPersistencePort;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.infrastructure.exception.*;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoriaEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoriaRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;


@RequiredArgsConstructor

public class PlatoJpaAdapter implements IPlatoPersistencePort, IPlatoModPersistencePort, ICambiarEstadoPlatoPersistencePort, IListarPlatoPersistencePort {

    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;
    private final ICategoriaRepository categoriaRepository;
    private final IRestaurantRepository restaurantRepository;



    @Override
    public Plato crear(Plato plato, int idPropietario) {
        Optional<CategoriaEntity> categoria = categoriaRepository.findById(plato.getIdCategoria());

        if(categoria.isEmpty()) {
            throw new CategoriaInexistente("La categoria no existe");
        }

        Optional<RestauranteEntity> restaurante = restaurantRepository.findById(plato.getIdRestaurante());
        if(restaurante.get().getIdPropietario() != idPropietario){
            throw new UsuarioNoPropietario("El usuario no es propietario del restaurante");
        }
        if(restaurante.isEmpty()){
            throw new RestauranteInexistente("El restaurante no existe");
        }

        if(platoRepository.buscarNombrePlato(plato.getNombre()).isPresent()){
            throw new PlatoExistente("El plato ya existe");
        }

        PlatoEntity nuevoPlato = platoRepository.save(platoEntityMapper.toEntity(plato));
        return platoEntityMapper.toPlatoModel(nuevoPlato);


    }


    @Override
    public Plato modificar(Plato platoModificado, int idPropietario) {
       PlatoEntity platoAMod = platoRepository.findById(platoModificado.getId()).orElseThrow(() -> new RuntimeException("Plato no encontrado con el id: " + platoModificado.getId()));
       Optional<RestauranteEntity> restauranteEntity = restaurantRepository.findById(platoAMod.getIdRestaurante());
       if(restauranteEntity.get().getIdPropietario() != idPropietario){
           throw new UsuarioNoPropietario("El usuario no es propietario del restaurante");
       }
       platoAMod.setDescripcion(platoModificado.getDescripcion());
       platoAMod.setPrecio(platoModificado.getPrecio());
       PlatoEntity response = platoRepository.save(platoAMod);
       return platoEntityMapper.toPlatoModel(response);

    }


    @Override
    public Plato cambiarEstado(int id, int idPropietario) {
        PlatoEntity platoEstado = platoRepository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado con el id: " + id));
        Optional<RestauranteEntity> restauranteEntity = restaurantRepository.findById(platoEstado.getIdRestaurante());
        if(restauranteEntity.get().getIdPropietario() != idPropietario){
            throw new UsuarioNoPropietario("El usuario no es propietario del restaurante");
        }

        platoEstado.setActivo(!platoEstado.getActivo());
        PlatoEntity response = platoRepository.save(platoEstado);
        return platoEntityMapper.toPlatoModel(response);
    }

    @Override
    public Page<Plato> listarPlato(PageRequest pageRequest, Integer idCategoria, int idRestaurante) {


        Page<PlatoEntity> listarPlato = platoRepository.buscarCategoriayRestaurante(pageRequest,idCategoria,idRestaurante);
        return listarPlato.map(this::toPlatoModel);
    }

    private Plato toPlatoModel(PlatoEntity platoEntity) {
        return new Plato(
                platoEntity.getId(),
                platoEntity.getNombre(),
                platoEntity.getIdCategoria(),
                platoEntity.getDescripcion(),
                platoEntity.getPrecio(),
                platoEntity.getIdRestaurante(),
                platoEntity.getUrlImagen(),
                platoEntity.getActivo()

        );
    }
    }
