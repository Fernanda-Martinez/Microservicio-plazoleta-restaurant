package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RealizarPedidoRequestDto;
import com.pragma.powerup.application.dto.response.*;
import com.pragma.powerup.application.handler.*;
import com.pragma.powerup.infrastructure.exception.ExceptionMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pedido")
@RequiredArgsConstructor
public class PedidoRestController {
    private final IPedidoHandler pedidoHandler;
    private final IListarPedidoHandler listarPedidoHandler;
    private final IAsignarPedidoHandler asignarPedidoHandler;
    private final ICancelarPedidoHandler cancelarPedidoHandler;
    private final ICambiarEstadoPedidoHandler cambiarEstadoPedidoHandler;
    private final ICambiarEntregadoHandler cambiarEntregadoHandler;

    @Operation(summary = "Crea un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El pedido no puedo crearse", content = @Content),
            @ApiResponse(responseCode = "403", description = "No se cumple con el rol requerido (CLIENTE)", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_cliente')")
    @PostMapping("/registrar")
    public ResponseEntity<RealizarPedidoResponseDto> registrar(@RequestBody RealizarPedidoRequestDto requestDto) throws ExceptionMessage {
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
        Page<ListarPedidoResponseDto> response = listarPedidoHandler.listarPedidos(idEmpleado, idRestaurante, estado, parametros);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_empleado')")
    @Operation(summary = "Asignarse un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido asignado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Asignación de pedido fallida", content = @Content)
    })
    @PutMapping("/asignar")
    public ResponseEntity<Page<AsignarPedidoResponseDto>> asignar(@RequestParam(defaultValue = "0") int idEmpleado, @RequestParam(defaultValue = "0") int idPedido,@RequestParam(defaultValue = "") String estado, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "0") int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<AsignarPedidoResponseDto> response = asignarPedidoHandler.asignar(idEmpleado, idPedido, estado, pageRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Cancelar un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cancelado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Cancelación de pedido fallida", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_cliente')")
    @PutMapping("/cancelar")
    public ResponseEntity<CancelarPedidoResponseDto> cancelar(@RequestParam(defaultValue = "0") int idCliente, @RequestParam(defaultValue = "0") int idPedido){
        CancelarPedidoResponseDto response = cancelarPedidoHandler.cancelarPedido(idCliente, idPedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Cambiar estado de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El pedido cambio de estado a listo", content = @Content),
            @ApiResponse(responseCode = "409", description = "Cambio de estado de pedido fallido", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_empleado')")
    @PutMapping("/cambiar_estado")
    public ResponseEntity<CambiarEstadoPedidoResponseDto> cambiarEstado(@RequestParam(defaultValue = "0") int idEmpleado, @RequestParam(defaultValue = "0") int idPedido){
        CambiarEstadoPedidoResponseDto response = cambiarEstadoPedidoHandler.cambiarEstadoPedido(idEmpleado, idPedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Cambiar estado a entregado de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El pedido cambio de estado a entregado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Cambio de estado de pedido fallido", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_empleado')")
    @PutMapping("/cambiar_entregado")
    public ResponseEntity<AsignarPedidoResponseDto> cambiarEntregado(@RequestParam(defaultValue = "0") int idEmpleado, @RequestParam(defaultValue = "0") int idPedido, @RequestParam(defaultValue = "") String pin){
        AsignarPedidoResponseDto response = cambiarEntregadoHandler.cambiarEntregado(idEmpleado, idPedido, pin);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
