package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
@Data
public class ConsultaResponse {

    private Long id;

    private String nombre;

    private String apellido;

    private String numTelefono;

    private String email;

    private String mensaje;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fecha;
}
