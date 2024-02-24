package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;
import com.pragma.powerup.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IRestauranteResponseMapper {
    CrearRestauranteResponseDto toResponse(Restaurante restaurante);

}
