package com.gestion.comercial.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CotizacionVentaRequest {


    @NotBlank(message = "El campo sucursal no puede estar vacio")
    private String sucursal;

    @NotBlank(message = "El campo nombre del cliente no puede estar vacio")
    @Size(min = 1, max = 15, message = "El campo nombre tiene un tamaño minimo de 1 y maximo de 15")
    private String nombreCliente;

    @NotBlank(message = "El campo patente del cliente no puede estar vacio")
    private String patente;

    @Email(message = "Error en el formato del campo Email")
    private String email;

    @NotNull(message = "El campo id del vendedor no puede estar vacio")
    private Integer idVendedor;

    @NotNull(message = "El campo garantia extendida no puede estar vacio")
    private Boolean garantiaExtendida;
}
