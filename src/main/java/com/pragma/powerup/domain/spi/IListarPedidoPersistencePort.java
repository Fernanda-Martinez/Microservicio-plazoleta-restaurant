package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IListarPedidoPersistencePort {
    Page<Pedido> listarPedidos (int idCliente, int idRestaurante, String estado, PageRequest pageRequest);
}
