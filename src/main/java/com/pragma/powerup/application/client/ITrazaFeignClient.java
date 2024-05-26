package com.pragma.powerup.application.client;

import com.pragma.powerup.application.dto.response.CrearTrazaResponseDto;
import com.pragma.powerup.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "Microservicio-plazoleta-traza", url="http://localhost:8084", configuration = FeignClientConfig.class)

public interface ITrazaFeignClient {

    @GetMapping(value = "/api/v1/traza/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    CrearTrazaResponseDto crear(
            @RequestHeader("Authorization") String token,
            @RequestParam("idEmpleado") int idEmpleado,
            @RequestParam("idPedido") int idPedido,
            @RequestParam("idCliente") int idCliente,
            @RequestParam("correoEmpleado") String correoEmpleado,
            @RequestParam("correoCliente") String correoCliente,
            @RequestParam("nuevoEstado") String nuevoEstado);
}
