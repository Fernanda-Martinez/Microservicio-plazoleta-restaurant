package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CrearRestauranteRequestDto;
import com.pragma.powerup.application.dto.response.CrearRestauranteResponseDto;
import com.pragma.powerup.application.dto.response.ListarRestauranteResponseDto;
import com.pragma.powerup.application.handler.IListarRestauranteHandler;
import com.pragma.powerup.application.handler.IRestauranteHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteRestController {

    private final IRestauranteHandler restauranteHandler;
    private final IListarRestauranteHandler listarRestauranteHandler;

    @Operation(summary = "Agrega un nuevo restaurante ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El restaurante ya existe", content = @Content),
            @ApiResponse(responseCode = "403", description = "Solo el administrador puede crear el restaurante", content = @Content)

    })
    @PostMapping("/crear")
    public ResponseEntity<CrearRestauranteResponseDto> crear(@RequestBody CrearRestauranteRequestDto restauranteRequestDto) {
        CrearRestauranteResponseDto response = restauranteHandler.crear(restauranteRequestDto);

        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();

    }


    //listar restaurante
    @Operation(summary = "Listar los restaurantes ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurantes listados", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuario no autorizado", content = @Content),
            @ApiResponse(responseCode = "409", description = "No hay restaurantes disponibles", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_cliente')")
    @GetMapping("/listar")
    public ResponseEntity<Page<ListarRestauranteResponseDto>> listarRestaurante(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize){
        try {
            PageRequest parametros = PageRequest.of(pageNumber,pageSize, Sort.by("nombre").ascending());
            Page<ListarRestauranteResponseDto> responseDto = listarRestauranteHandler.listarRestaurante(parametros);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Devuelve un error 403 Forbidden
        }
    }


}
