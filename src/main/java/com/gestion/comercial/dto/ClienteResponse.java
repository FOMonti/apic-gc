package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class ClienteResponse {

    private String dni;

    private String nombre;

    private String apellido;

    private String email;

    private String direccion;

    private String numTelefono;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fecha;
}
