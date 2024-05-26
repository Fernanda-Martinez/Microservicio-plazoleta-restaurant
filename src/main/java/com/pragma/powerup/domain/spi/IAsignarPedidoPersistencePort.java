package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IAsignarPedidoPersistencePort {
    Page<Pedido> asignar(int idEmpleado, int idPedido, String estado, PageRequest pageRequest);

    Pedido buscarPedido(int idPedido);
}
