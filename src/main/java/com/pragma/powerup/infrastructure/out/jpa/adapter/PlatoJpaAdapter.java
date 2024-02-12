package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.IPlatoModPersistencePort;
import com.pragma.powerup.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor

public class PlatoJpaAdapter implements IPlatoPersistencePort, IPlatoModPersistencePort {

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
}
