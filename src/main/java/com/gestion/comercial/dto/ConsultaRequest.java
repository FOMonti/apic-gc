package com.gestion.comercial.dto;

import jakarta.persistence.Column;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ConsultaRequest {

    @NotBlank(message = "El campo nombre no puede estar vacio")
    private String nombre;

    private String apellido;

    private String numTelefono;

    @Email(message = "Error en el formato del campo Email")
    private String email;

    @Size(min = 1, max = 150, message = "El campo mensaje tiene un tamaño minimo de 1 y maximo de 150 caracteres")
    private String mensaje;
}
