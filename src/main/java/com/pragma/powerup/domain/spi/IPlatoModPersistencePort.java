package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Plato;

public interface IPlatoModPersistencePort {

    Plato modificar(Plato platoModificado);
}
