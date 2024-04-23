package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.spi.IAsignarPedidoPersistencePort;
import com.pragma.powerup.domain.spi.ICancelarPedidoPersistencePort;
import com.pragma.powerup.domain.spi.IListarPedidoPersistencePort;
import com.pragma.powerup.domain.spi.IPedidoPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoPedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPedidoRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor

public class PedidoJpaAdapter implements IPedidoPersistencePort, IListarPedidoPersistencePort,
        IAsignarPedidoPersistencePort, ICancelarPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final IPlatoPedidoRepository platoPedidoRepository;
    String PENDIENTE_CONST = "Pendiente";
    String CANCELADO_CONST = "Cancelado";
    String PREPARACION_CONST = "En Preparación";



    @Override
    public Pedido registrar(Pedido pedidoRegistrado) {
        List<PedidoEntity> pedidos = pedidoRepository.findAll();

        if (pedidos.stream().anyMatch(item ->
                item.getIdCliente() == pedidoRegistrado.getIdCliente() &&
                        PENDIENTE_CONST.equals(item.getEstado()))) {
            return null;
        }


        PedidoEntity pedido = new PedidoEntity();
        pedido.setEstado(PENDIENTE_CONST);
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

    @Override
    public Page<Pedido> listarPedidos(int idEmpleado, int idRestaurante, String estado, PageRequest pageRequest){
        Page<PedidoEntity> pedidos = pedidoRepository.buscarEstadoPedido(pageRequest,estado,idRestaurante);
        return pedidos.map(this::toPedidoModel);
    }

    private Pedido toPedidoModel(PedidoEntity entity){
        List<PlatoRequestDto> list = new ArrayList<>();

        return new Pedido(
                entity.getId(),
                entity.getIdCliente(),
                entity.getIdRestaurante(),
                entity.getEstado(),
                entity.getFecha(),
                entity.getIdChef(),
                list
        );
    }

    @Override
    public Pedido asignar(int idEmpleado, int idPedido) {
        Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(idPedido);

        if(pedidoOptional.isEmpty()){
            return null;
        }

        PedidoEntity pedido = pedidoOptional.get();
        pedido.setIdChef(idEmpleado);
        pedido.setEstado(PREPARACION_CONST);

        pedidoRepository.save(pedido);
        List<PlatoRequestDto> list = new ArrayList<>();

        return new Pedido(pedido.getId(), pedido.getIdCliente(),
                pedido.getIdRestaurante(), pedido.getEstado(),
                pedido.getFecha(), pedido.getIdChef(), list);
    }

    @Override
    public String cancelarPedido(int idCliente, int idPedido) {
        Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(idPedido);

        if (pedidoOptional.isEmpty()) {
            return "El pedido no se encuentra registrado";
        }

        if(pedidoOptional.get().getIdCliente() != idCliente){
            return "El pedido no está asignado al cliente";
        }

        PedidoEntity pedido = pedidoOptional.get();
        if(!pedido.getEstado().equals(PENDIENTE_CONST)){
            return "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse";
        }
        pedido.setEstado(CANCELADO_CONST);

        pedidoRepository.save(pedido);


        return "Pedido cancelado con exito";
    }
}
