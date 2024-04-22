package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IListarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IListarPedidoPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class ListarPedidoUseCase implements IListarPedidoServicePort {
    private final IListarPedidoPersistencePort listarPedidoPersistencePort;

    public ListarPedidoUseCase(IListarPedidoPersistencePort listarPedidoPersistencePort) {
        this.listarPedidoPersistencePort = listarPedidoPersistencePort;
    }

    @Override
    public Page<Pedido> listarPedidos(int idEmpleado, int idRestaurante, String estado, PageRequest pageRequest) {
        return this.listarPedidoPersistencePort.listarPedidos(idEmpleado, idRestaurante, estado, pageRequest);
    }
}
