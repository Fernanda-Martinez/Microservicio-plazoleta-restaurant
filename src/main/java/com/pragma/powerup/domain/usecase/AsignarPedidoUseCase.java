package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IAsignarPedidoServicePort;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IAsignarPedidoPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public class AsignarPedidoUseCase implements IAsignarPedidoServicePort {
    private final IAsignarPedidoPersistencePort asignarPedidoPersistencePort;

    public AsignarPedidoUseCase(IAsignarPedidoPersistencePort asignarPedidoPersistencePort) {
        this.asignarPedidoPersistencePort = asignarPedidoPersistencePort;
    }

    @Override
    public Page<Pedido> asignar(int idEmpleado, int idPedido, String estado,PageRequest pageRequest) {
        return this.asignarPedidoPersistencePort.asignar(idEmpleado, idPedido, estado, pageRequest);
    }

    @Override
    public Pedido buscarPedido(int idPedido) {
        return this.asignarPedidoPersistencePort.buscarPedido(idPedido);
    }
}
