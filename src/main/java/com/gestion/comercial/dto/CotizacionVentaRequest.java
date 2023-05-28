package com.gestion.comercial.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CotizacionVentaRequest {


    @NotBlank(message = "El campo sucursal no puede estar vacio")
    @Size(min = 1, max = 4, message = "El campo sucursal tiene un tamaño minimo de 1 y maximo de 15")
    private String sucursal;

    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    @Size(max = 20, message = "El nombre del cliente debe tener como máximo {max} caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre del cliente solo puede contener letras y espacios")
    private String nombreCliente;

    @NotBlank(message = "La patente no puede estar vacía")
    @Size(max = 10, message = "La patente debe tener como máximo {max} caracteres")
    @Pattern(regexp = "^([A-Z]{2,3}-\\d{3}(?:-[A-Z]{2})?)$", message = "La patente no tiene un formato válido")
    private String patente;

    @Email(message = "Error en el formato del Email")
    @Size(max = 70, message = "El campo Email debe tener como máximo {max} caracteres")
    private String email;

    @NotNull(message = "El campo id del vendedor no puede estar vacio")
    @Digits(integer = 5, fraction = 0, message = "El campo idVendedor debe tener como máximo {integer} dígitos")
    private Integer idVendedor;

    @NotNull(message = "El campo garantia extendida no puede estar vacío")
    private Boolean garantiaExtendida;
}
