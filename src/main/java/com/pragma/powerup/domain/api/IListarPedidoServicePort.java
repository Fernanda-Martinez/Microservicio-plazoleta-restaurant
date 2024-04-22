package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IListarPedidoServicePort {
    Page<Pedido> listarPedidos(int idEmpleado, int idRestaurante, String estado, PageRequest pageRequest);
}
