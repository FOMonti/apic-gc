package com.gestion.comercial.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequest {

    @Size(max = 8, message = "El campo DNI debe tener exactamente {max} caracteres")
    private String dni;

    @Size(min = 1, max = 20, message = "El campo nombre tiene un tamaño minimo de {min} y maximo de {max}")
    private String nombre;

    @Size(min = 1, max = 20, message = "El campo apellido tiene un tamaño minimo de {min} y maximo de {max}")
    private String apellido;

    private String numTelefono;

    @Email(message = "Error en el formato del campo Email")
    private String email;

    private String direccion;
}
