package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.Pedido;
import com.pragma.powerup.domain.model.Restaurante;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.infrastructure.exception.*;
import com.pragma.powerup.infrastructure.out.jpa.entity.PedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlatoPedidoEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestauranteEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPedidoRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoPedidoRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlatoRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
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
        IAsignarPedidoPersistencePort, ICancelarPedidoPersistencePort, ICambiarEstadoPedidoPersistencePort,
        ICambiarEntregadoPersistencePort{

    private final IPedidoRepository pedidoRepository;
    private final IPlatoPedidoRepository platoPedidoRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IPlatoRepository platoRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    String PENDIENTE_CONST = "Pendiente";
    String CANCELADO_CONST = "Cancelado";
    String PREPARACION_CONST = "En Preparación";
    String ENTREGADO_CONST = "Entregado";

    @Override

    public Pedido registrar(Pedido pedidoRegistrado) throws ExceptionMessage {
        List<PedidoEntity> pedidos = pedidoRepository.findAll();
        if(restaurantRepository.findById(pedidoRegistrado.getIdRestaurante()).isEmpty()){
            throw new RestauranteInexistente("El restaurante del pedido no existe");
        }

        if (pedidos.stream().anyMatch(item ->
                item.getIdCliente() == pedidoRegistrado.getIdCliente() &&
                        (!ENTREGADO_CONST.equals(item.getEstado())))) {
            throw new ExceptionMessage("El cliente ya tiene un pedido en proceso");
        }

        List<PlatoEntity> platos = platoRepository.findAll();

        boolean platoNoEncontrado = pedidoRegistrado.getPlatoRequestDtoList().stream()
                .noneMatch(dto -> platos.stream()
                        .anyMatch(plato -> plato.getId() == dto.getIdPlato() && plato.getIdRestaurante() == pedidoRegistrado.getIdRestaurante()));

        if (platoNoEncontrado) {
            throw new PlatoNoRestaurante("El plato no existe para ese restaurante");
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
    public Page<Pedido> asignar(int idEmpleado, int idPedido, String estado,PageRequest pageRequest) {
        Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(idPedido);
        PedidoEntity pedido = pedidoOptional.get();
        pedido.setIdChef(idEmpleado);
        pedido.setEstado(PREPARACION_CONST);

        pedidoRepository.save(pedido);
        List<PlatoRequestDto> list = new ArrayList<>();

        Page<PedidoEntity> response = pedidoRepository.buscarEstadoPedido(pageRequest,estado,pedido.getIdRestaurante());;

        return response.map(this::toPedidoModel);
    }

    @Override
    public Pedido buscarPedido(int idPedido) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);

        if(pedido.isEmpty()){
            throw new PedidoInexistente("El pedido no existe");
        }

        List<PlatoRequestDto> list = new ArrayList<>();

        return new Pedido(pedido.get().getId(), pedido.get().getIdCliente(),
                pedido.get().getIdRestaurante(), pedido.get().getEstado(),
                pedido.get().getFecha(), pedido.get().getIdChef(), list);
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

    @Override
    public Pedido cambiarEstadoPedido(int idEmpleado, int idPedido) {
        Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(idPedido);
        PedidoEntity pedido = pedidoOptional.get();

        if(pedido.getIdChef() != idEmpleado){
            throw new EmpleadoNoAsignado("El empleado no tiene este pedido asignado");
        }
        pedido.setEstado(Constants.READY);




        pedidoRepository.save(pedido);
        List<PlatoRequestDto> list = new ArrayList<>();

        return new Pedido(pedido.getId(), pedido.getIdCliente(), pedido.getIdRestaurante(),
                pedido.getEstado(), pedido.getFecha(), pedido.getIdChef(), list);

    }

    @Override
    public Pedido asignarPin(int idPedido, String pin) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);

        pedido.get().setPin(pin);
        List<PlatoRequestDto> list = new ArrayList<>();

        return new Pedido(pedido.get().getId(), pedido.get().getIdCliente(),
                pedido.get().getIdRestaurante(), pedido.get().getEstado(),
                pedido.get().getFecha(), pedido.get().getIdChef(), list);
    }

    @Override
    public Restaurante obtenerRestaurante(int idRestaurante) {
        Optional<RestauranteEntity> restauranteEntity = restaurantRepository.findById(idRestaurante);

        if(restauranteEntity.isEmpty()){
            throw new RestauranteInexistente("El restaurante no existe");
        }

        return restauranteEntityMapper.toRestauranteModel(restauranteEntity.get());
    }

    @Override
    public Pedido cambiarEntregado(int idEmpleado, int idPedido, String pin) {

        Optional<PedidoEntity> pedidoEntity = pedidoRepository.findById(idPedido);

        if(pedidoEntity.isEmpty()){
            throw new PedidoInexistente("El pedido no existe");
        }

        if(pedidoEntity.get().getIdChef() != idEmpleado){
            throw new EmpleadoNoAsignado("El empleado no está asignado al pedido");
        }

        if(!pedidoEntity.get().getPin().equals(pin)) {
            throw new PinIncorrecto("El pin de este pedido es incorrecto");
        }

        if(!pedidoEntity.get().getEstado().equals(Constants.READY)){
            throw new EstadoIncorrecto("El estado no puede ser diferente a listo");
        }

        pedidoEntity.get().setEstado(Constants.DELIVERED);

         List<PlatoRequestDto> list = new ArrayList<>();

        return new Pedido(pedidoEntity.get().getId(), pedidoEntity.get().getIdCliente(),
                pedidoEntity.get().getIdRestaurante(), pedidoEntity.get().getEstado(),
                pedidoEntity.get().getFecha(), pedidoEntity.get().getIdChef(), list);
    }
}
