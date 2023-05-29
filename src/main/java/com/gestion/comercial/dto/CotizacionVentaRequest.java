package com.gestion.comercial.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CotizacionVentaRequest {

    @Size(min = 1, max = 4, message = "El campo sucursal tiene un tamaño minimo de 1 y maximo de 15")
    private String sucursal;

    @Size(max = 20, message = "El nombre del cliente debe tener como máximo {max} caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre del cliente solo puede contener letras y espacios")
    private String nombreCliente;

    @Size(max = 10, message = "La patente debe tener como máximo {max} caracteres")
    @Pattern(regexp = "^([A-Z]{2,3}-\\d{3}(?:-[A-Z]{2})?)$", message = "La patente no tiene un formato válido")
    private String patente;

    @Email(message = "Error en el formato del Email")
    @Size(max = 70, message = "El campo Email debe tener como máximo {max} caracteres")
    private String email;

    @NotNull(message = "El campo id del vendedor no puede estar vacio")
    @Digits(integer = 5, fraction = 0, message = "El campo idVendedor debe tener como máximo {integer} dígitos enteros")
    private BigDecimal idVendedor;

    @NotNull(message = "El campo garantia extendida no puede estar vacío")
    private Boolean garantiaExtendida;
}