package com.pragma.powerup.domain.spi;


public interface ICancelarPedidoPersistencePort {
    String cancelarPedido(int idCliente, int idPedido);
}
