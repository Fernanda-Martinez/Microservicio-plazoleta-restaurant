package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.response.RealizarPedidoResponseDto;
import com.pragma.powerup.application.handler.IPedidoHandler;
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
@RequestMapping("/api/v1/pedido")
@RequiredArgsConstructor
public class PedidoRestController {
    private final IPedidoHandler pedidoHandler;

    @Operation(summary = "Crea un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El pedido no puedo crearse", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<RealizarPedidoResponseDto> registrar(@RequestBody RealizarPedidoRequestDto requestDto) {
        RealizarPedidoResponseDto pedidoResponseDto = pedidoHandler.registrar(requestDto);
        return new ResponseEntity<>(pedidoResponseDto, HttpStatus.OK);
    }

}
