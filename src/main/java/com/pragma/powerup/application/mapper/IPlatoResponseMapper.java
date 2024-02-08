package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.CrearPlatoResponseDto;
import com.pragma.powerup.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IPlatoResponseMapper {
    CrearPlatoResponseDto toResponse(Plato plato);
}
