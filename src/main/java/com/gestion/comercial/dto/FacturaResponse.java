package com.gestion.comercial.dto;

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

    private Timestamp fechaCreacion;

    private Timestamp fechaPago;
}
