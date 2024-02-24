package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IListarRestauranteServicePort {
    Page<Restaurante> listarRestaurante(PageRequest pageRequest);
}
