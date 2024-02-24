package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Plato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IListarPlatoServicePort {
    Page<Plato> listarPlato (PageRequest pageRequest, Integer idCategoria, int idRestaurante);
}
