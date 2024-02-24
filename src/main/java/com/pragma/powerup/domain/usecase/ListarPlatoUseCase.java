package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IListarPlatoServicePort;
import com.pragma.powerup.domain.model.Plato;
import com.pragma.powerup.domain.spi.IListarPlatoPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public class ListarPlatoUseCase implements IListarPlatoServicePort {

    private final IListarPlatoPersistencePort listarPlatoPersistencePort;

    public ListarPlatoUseCase(IListarPlatoPersistencePort listarPlatoPersistencePort) {
        this.listarPlatoPersistencePort = listarPlatoPersistencePort;
    }

    @Override
    public Page<Plato> listarPlato(PageRequest pageRequest, Integer idCategoria, int idRestaurante) {
        return this.listarPlatoPersistencePort.listarPlato(pageRequest, idCategoria, idRestaurante);
    }
}
