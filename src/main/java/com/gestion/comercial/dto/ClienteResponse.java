package com.gestion.comercial.dto;

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

    private Timestamp fecha;
}
