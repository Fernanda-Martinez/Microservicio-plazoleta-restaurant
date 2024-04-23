package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.response.AsignarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.CancelarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.ListarPedidoResponseDto;
import com.pragma.powerup.application.dto.response.RealizarPedidoResponseDto;
import com.pragma.powerup.application.handler.IAsignarPedidoHandler;
import com.pragma.powerup.application.handler.ICancelarPedidoHandler;
import com.pragma.powerup.application.handler.IListarPedidoHandler;
import com.pragma.powerup.application.handler.IPedidoHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pedido")
@RequiredArgsConstructor
public class PedidoRestController {
    private final IPedidoHandler pedidoHandler;
    private final IListarPedidoHandler listarPedidoHandler;
    private final IAsignarPedidoHandler asignarPedidoHandler;
    private final ICancelarPedidoHandler cancelarPedidoHandler;

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

    @Operation(summary = "Listar pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El pedido no puedo crearse", content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<Page<ListarPedidoResponseDto>> listar(@RequestParam(defaultValue = "0") int idEmpleado,
                                                          @RequestParam(defaultValue = "0") int idRestaurante,
                                                          @RequestParam(defaultValue = "") String estado,
                                                          @RequestParam(defaultValue = "0") int pageNumber,
                                                          @RequestParam(defaultValue = "0") int pageSize
                                                          ) {
        PageRequest parametros = PageRequest.of(pageNumber, pageSize);
        Page<ListarPedidoResponseDto> response = listarPedidoHandler.listarPlatos(idEmpleado, idRestaurante, estado, parametros);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Asignarse un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido asignado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Asignación de pedido fallida", content = @Content)
    })
    @PutMapping("/asignar")
    public ResponseEntity<AsignarPedidoResponseDto> asignar(@RequestParam(defaultValue = "0") int idEmpleado, @RequestParam(defaultValue = "0") int idPedido){
        AsignarPedidoResponseDto response = asignarPedidoHandler.asignar(idEmpleado, idPedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Cancelar un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cancelado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Cancelación de pedido fallida", content = @Content)
    })
    @PutMapping("/cancelar")
    public ResponseEntity<CancelarPedidoResponseDto> cancelar(@RequestParam(defaultValue = "0") int idCliente, @RequestParam(defaultValue = "0") int idPedido){
        CancelarPedidoResponseDto response = cancelarPedidoHandler.cancelarPedido(idCliente, idPedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
