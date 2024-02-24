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
            @ApiResponse(responseCode = "409", description = "El restaurante ya existe", content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<CrearRestauranteResponseDto> crear(@RequestBody CrearRestauranteRequestDto restauranteRequestDto) {
        CrearRestauranteResponseDto responseDto = restauranteHandler.crearRestaurante(restauranteRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    //listar restaurante
    @Operation(summary = "Listar los restaurantes ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurantes listados", content = @Content),
            @ApiResponse(responseCode = "409", description = "No hay restaurantes disponibles", content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<Page<ListarRestauranteResponseDto>> listarRestaurante(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize){
        PageRequest parametros = PageRequest.of(pageNumber,pageSize, Sort.by("nombre").ascending());
        Page<ListarRestauranteResponseDto> responseDto = listarRestauranteHandler.listarRestaurante(parametros);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }


}
