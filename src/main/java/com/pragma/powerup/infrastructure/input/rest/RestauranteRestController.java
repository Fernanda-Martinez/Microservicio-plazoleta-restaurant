package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CrearRestauranteRequestDto;
import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;
import com.pragma.powerup.application.handler.IRestauranteHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteRestController {

    private final IRestauranteHandler restauranteHandler;

    @Operation(summary = "Agrega un nuevo restaurante ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El restaurante ya existe", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<CrearRestauranteResponseDto> crear(@RequestBody CrearRestauranteRequestDto restauranteRequestDto) {
        CrearRestauranteResponseDto responseDto = restauranteHandler.crearRestaurante(restauranteRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }


}
