package com.gestion.comercial.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ConsultaRequest {

    @Size(min = 1, max = 20, message = "El campo nombre tiene un tamaño minimo de {min} y maximo de {max}")
    private String nombre;

    private String apellido;

    private String numTelefono;

    @Email(message = "Error en el formato del campo Email")
    private String email;

    @Size(min = 1, max = 150, message = "El campo mensaje tiene un tamaño minimo de 1 y maximo de 150 caracteres")
    private String mensaje;
}
