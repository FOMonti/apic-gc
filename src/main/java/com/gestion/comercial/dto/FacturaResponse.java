package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gestion.comercial.types.EstadoFactura;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FacturaResponse {

    private Long id;

    private String sucursal;

    private Long numeroFactura;

    private Integer idVendedor;

    private String patente;

    private Long cotizacionID;

    private String clienteDni;

    private Long reservaId;

    private Double importe;

    private EstadoFactura estado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fechaCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fechaPago;

    private PlanResponse planResponse;
}
