package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;
@Data
public class CotizacionVentaResponse {
    private Long id;

    private String sucursal;

    private Long numeroCotizacion;

    private Integer idVendedor;

    private String patente;

    private String dniCliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fecha;

    private Double precioBase;

    private Double precioTraslado;

    private Double importeIVA;

    private Double gastosAdministrativos;

    private Double gastosGarantia;

    private Boolean garatiaExtendida;

    private Double total;

}
