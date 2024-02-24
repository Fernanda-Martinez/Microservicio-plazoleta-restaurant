package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CrearPlatoRequestDto;
import com.pragma.powerup.application.dto.request.ModificarPlatoRequestDto;
import com.pragma.powerup.application.dto.response.*;
import com.pragma.powerup.application.handler.IListarPlatoHandler;
import com.pragma.powerup.application.handler.IPlatoEstadoHandler;
import com.pragma.powerup.application.handler.IPlatoHandler;
import com.pragma.powerup.application.handler.IPlatoModifHandler;
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
@RequestMapping("/api/v1/platos")
@RequiredArgsConstructor

public class PlatoRestController {
    private final IPlatoHandler platoHandler;
    private final IPlatoModifHandler platoModifHandler;
    private final IPlatoEstadoHandler platoEstadoHandler;
    private final IListarPlatoHandler listarPlatoHandler;

    @Operation(summary = "Crear un nuevo plato para asociarlos al menú de mi restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El plato ya existe", content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<CrearPlatoResponseDto> crear(@RequestBody CrearPlatoRequestDto platoRequestDto) {
        CrearPlatoResponseDto platoResponseDto = platoHandler.crearPlato(platoRequestDto);
        return new ResponseEntity<>(platoResponseDto, HttpStatus.CREATED);
    }


    @Operation(summary = "Modificar un plato del menú de mi restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El plato se modifico", content = @Content),
            @ApiResponse(responseCode = "409", description = "El plato no existe", content = @Content)
    })
    @PutMapping("/modificar")
    public ResponseEntity<ModificarPlatoResponseDto> modificar(@RequestBody ModificarPlatoRequestDto modificarPlatoRequestDto){
        ModificarPlatoResponseDto modificarPlatoResponseDto = platoModifHandler.modificarPlato((modificarPlatoRequestDto));
        return new ResponseEntity<>(modificarPlatoResponseDto,HttpStatus.OK);
    }

    @Operation(summary = "Cambiar el estado de un plato del menú de mi restaurante (Activo/Inactivo)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El plato cambio su estado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El plato no existe", content = @Content)
    })
    @PutMapping("/cambiarEstado")
    public ResponseEntity<CambiarEstadoPlatoResponseDto> cambiarEstado(@RequestParam(name = "id") int id){
        CambiarEstadoPlatoResponseDto cambiarEstadoPlatoResponseDto = platoEstadoHandler.cambiarEstadoPlato(id);
        return new ResponseEntity<>(cambiarEstadoPlatoResponseDto,HttpStatus.OK);
    }

    //listar plato
    @Operation(summary = "Listar los platos de un restaurante ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "platos listados", content = @Content),
            @ApiResponse(responseCode = "409", description = "No hay platos en la base de datos", content = @Content)
    })

    @GetMapping("/listar")
    public ResponseEntity<Page<ListarPlatosResponseDto>> listarPlato(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) Integer idCategoria,
                                                                     @RequestParam int idRestaurante){
        PageRequest parametros = PageRequest.of(pageNumber,pageSize, Sort.by("nombre").ascending());
        Page<ListarPlatosResponseDto> responseDto = listarPlatoHandler.listarPlatos(parametros,idCategoria,idRestaurante);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
