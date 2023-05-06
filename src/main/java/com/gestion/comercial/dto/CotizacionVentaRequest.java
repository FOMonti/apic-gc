package com.gestion.comercial.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CotizacionVentaRequest {


    @NotBlank(message = "errorS")
    private String sucursal;

    @NotBlank(message = "errorDC")
    private String dniCliente;

    @NotBlank(message = "errorP")
    private String patente;

    @NotNull(message = "errorV")
    private Integer idVendedor;

    @NotNull(message = "errorGE")
    private Boolean garantiaExtendida;
}
