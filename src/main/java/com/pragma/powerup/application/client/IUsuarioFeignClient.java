package com.pragma.powerup.application.client;

import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "Microservicio-plazoleta-users", url="http://localhost:8081", configuration = FeignClientConfig.class)

public interface IUsuarioFeignClient {

    @GetMapping(value = "/feign/admin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean validateAdminRole(@RequestHeader("Authorization") String token, @PathVariable int id);

    @GetMapping(value = "/feign/owner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean validateOwnerRole(@RequestHeader("Authorization") String token, @PathVariable int id);

    @GetMapping(value = "/feign/client/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean validateClientRole(@RequestHeader("Authorization") String token, @PathVariable int id);

    @GetMapping(value = "/feign/employeeRestaurant/{idEmpleado}/{idRestaurante}", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean validateEmployeeRestaurant(@RequestHeader("Authorization") String token, @PathVariable int idEmpleado, @PathVariable int idRestaurante);

    @GetMapping(value = "/feign/get_user/{idUsuario}", consumes = MediaType.APPLICATION_JSON_VALUE)
    UserInfoResponseDto getUser(@RequestHeader("Authorization") String token, @PathVariable int idUsuario);

}

