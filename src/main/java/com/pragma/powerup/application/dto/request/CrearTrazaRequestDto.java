package com.pragma.powerup.application.dto.request;
import lombok.Data;

@Data
public class CrearTrazaRequestDto {
    int idEmpleado;
    int idPedido;
    String idCliente;
    String correoCliente;
    String correoEmpleado;
    String nuevoEstado;

}
