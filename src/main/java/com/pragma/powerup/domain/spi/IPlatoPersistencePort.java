package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Plato;

public interface IPlatoPersistencePort {

    Plato crear(Plato plato, int idPropietario);
}
