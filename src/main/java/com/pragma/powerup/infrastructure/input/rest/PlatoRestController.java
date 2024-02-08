package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CrearPlatoRequestDto;
import com.pragma.powerup.application.handler.IPlatoHandler;
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
@RequestMapping("/api/v1/platos")
@RequiredArgsConstructor

public class PlatoRestController {
    private final IPlatoHandler platoHandler;

    @Operation(summary = "Crear un nuevo plato para asociarlos al menú de mi restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El plato ya existe", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> crear(@RequestBody CrearPlatoRequestDto platoRequestDto) {
        platoHandler.crearPlato(platoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
