package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoRequestMapper {
    Pedido toPedido(RealizarPedidoRequestDto requestDto);
}
