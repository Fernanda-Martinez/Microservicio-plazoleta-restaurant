package com.pragma.powerup.application.client;

import com.pragma.powerup.application.dto.response.EnviarMensajeResponseDto;
import com.pragma.powerup.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "Microservicio-plazoleta-sms", url="http://localhost:8083", configuration = FeignClientConfig.class)

public interface IMensajeFeignClient {


    @GetMapping(value = "/api/v1/msg/enviar/{nombre}/{telefono}/{restaurante}", consumes = MediaType.APPLICATION_JSON_VALUE)
    EnviarMensajeResponseDto enviar(@PathVariable String nombre, @PathVariable String telefono, @PathVariable String restaurante);
}
