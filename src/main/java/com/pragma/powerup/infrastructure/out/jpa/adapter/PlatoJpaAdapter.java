package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.ICambiarEstadoPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IListarPlatoPersistencePort;
import com.pragma.powerup.domain.spi.IPlatoModPersistencePort;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@RequiredArgsConstructor

public class PlatoJpaAdapter implements IPlatoPersistencePort, IPlatoModPersistencePort, ICambiarEstadoPlatoPersistencePort, IListarPlatoPersistencePort {

    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;



    @Override
    public Plato crear(Plato plato) {
        PlatoEntity nuevoPlato = platoRepository.save(platoEntityMapper.toEntity(plato));
        return platoEntityMapper.toPlatoModel(nuevoPlato);

    }


    @Override
    public Plato modificar(Plato platoModificado) {
       PlatoEntity platoAMod = platoRepository.findById(platoModificado.getId()).orElseThrow(() -> new RuntimeException("Plato no encontrado con el id: " + platoModificado.getId()));
       platoAMod.setDescripcion(platoModificado.getDescripcion());
       platoAMod.setPrecio(platoModificado.getPrecio());
       PlatoEntity response = platoRepository.save(platoAMod);
       return platoEntityMapper.toPlatoModel(response);

    }


    @Override
    public Plato cambiarEstado(int id) {
        PlatoEntity platoEstado = platoRepository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado con el id: " + id));
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
