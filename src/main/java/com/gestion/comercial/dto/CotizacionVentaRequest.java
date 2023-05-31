package com.gestion.comercial.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CotizacionVentaRequest {

    @Size(min = 3, max = 4, message = "La sucursal tiene un tamaño minimo de {min} y maximo de {max} caracteres")
    private String sucursal;

    @Size(min = 1, max = 20, message = "El nombre del cliente tiene un tamaño minimo de {min} y maximo de {max} caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre del cliente solo puede contener letras y espacios")
    private String nombreCliente;

    @Size(min = 7, max = 10, message = "La patente tiene un tamaño minimo de {min} y maximo de {max} caracteres")
    @Pattern(regexp = "^([A-Z]{2,3}-\\d{3}(?:-[A-Z]{2})?)$", message = "La patente no tiene un formato válido. Los formatos validos son: 'AAA-123' o 'AA-123-BB'")
    private String patente;

    @Email(message = "El formato del email no es valido")
    @Size(max = 70, message = "El campo Email debe tener como máximo {max} caracteres")
    private String email;

    @NotNull(message = "El campo id del vendedor no puede estar vacio")
    @Digits(integer = 5, fraction = 0, message = "El campo idVendedor debe tener como máximo {integer} dígitos enteros y {fraction} decimales")
    private BigDecimal idVendedor;

    @NotNull(message = "El campo id del vendedor no puede estar vacio")
    @Digits(integer = 9, fraction = 2, message = "El campo idVendedor debe tener como máximo {integer} dígitos enteros y {fraction} decimales")
    private BigDecimal precioBase;

    @NotNull(message = "El campo garantia extendida no puede estar vacío")
    private Boolean garantiaExtendida;
}