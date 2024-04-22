package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IPedidoPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoPedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPedidoRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoPedidoRepository;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final IPlatoPedidoRepository platoPedidoRepository;



    @Override
    public Pedido registrar(Pedido pedidoRegistrado) {
        List<PedidoEntity> pedidos = pedidoRepository.findAll();

        if (pedidos.stream().anyMatch(item ->
                item.getIdCliente() == pedidoRegistrado.getIdCliente() &&
                        "Pendiente".equals(item.getEstado()))) {
            return null;
        }


        PedidoEntity pedido = new PedidoEntity();
        pedido.setEstado("Pendiente");
        pedido.setFecha(new Date());
        pedido.setIdChef(0);
        pedido.setIdCliente(pedidoRegistrado.getIdCliente());
        pedido.setIdRestaurante(pedidoRegistrado.getIdRestaurante());

        PedidoEntity response = pedidoRepository.save(pedido);
        List<PlatoPedidoEntity> platoPedidoEntityList = new ArrayList<>();

        for(PlatoRequestDto platoRequestDto : pedidoRegistrado.getPlatoRequestDtoList()){
            PlatoPedidoEntity platoPedido = new PlatoPedidoEntity();
            platoPedido.setIdPedidos(response.getId());
            platoPedido.setIdPlato(platoRequestDto.getIdPlato());
            platoPedido.setCantidad(platoRequestDto.getCantidad());
            platoPedidoEntityList.add(platoPedido);
        }

        List<PlatoPedidoEntity> response2  = platoPedidoRepository.saveAll(platoPedidoEntityList);

        if(response2.isEmpty()){
            return null;
        }

        return mapToPedido(response, response2);
    }

    private Pedido mapToPedido(PedidoEntity pedido, List<PlatoPedidoEntity> platoPedido) {
        Pedido res = new Pedido();
        res.setId(pedido.getId());
        res.setIdRestaurante(pedido.getIdRestaurante());
        res.setIdCliente(pedido.getIdCliente());
        res.setEstado(pedido.getEstado());

        List<PlatoRequestDto> listRequest = platoPedido.stream()
                .map(item -> {
                    PlatoRequestDto request = new PlatoRequestDto();
                    request.setIdPlato(item.getIdPlato());
                    request.setCantidad(item.getCantidad());
                    return request;
                })
                .collect(Collectors.toList());

        res.setPlatoRequestDtoList(listRequest);

        return res;
    }

}
