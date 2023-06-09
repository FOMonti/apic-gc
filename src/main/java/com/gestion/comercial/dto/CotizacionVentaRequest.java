package com.gestion.comercial.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CotizacionVentaRequest {

    @Size(min = 3, max = 4, message = "La sucursal tiene un tamaño minimo de {min} y maximo de {max} caracteres")
    private String sucursal;

    @Size(min = 6, max = 10, message = "La patente tiene un tamaño minimo de {min} y maximo de {max} caracteres")
    private String patente;

    @Size(min = 8, max = 8, message = "El campo dni tiene un tamaño minimo de {min} y maximo de {max} caracteres")
    private String dni;

    @NotNull(message = "El campo id del vendedor no puede estar vacio")
    @Digits(integer = 5, fraction = 0, message = "El campo idVendedor debe tener como máximo {integer} dígitos enteros y {fraction} decimales")
    private BigDecimal idVendedor;

    @NotNull(message = "El campo garantia extendida no puede estar vacío")
    private Boolean garantiaExtendida;
}