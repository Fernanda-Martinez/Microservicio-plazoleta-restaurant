package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Plato;

public interface ICambiarEstadoPlatoServicePort {
    Plato cambiarEstado(int id, int idPropietario);
}
