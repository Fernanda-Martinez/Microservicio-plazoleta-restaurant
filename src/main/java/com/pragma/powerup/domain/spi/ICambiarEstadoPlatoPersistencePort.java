package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Plato;

public interface ICambiarEstadoPlatoPersistencePort {
    Plato cambiarEstado(int id, int idPropietario);

}
