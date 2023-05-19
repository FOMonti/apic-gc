package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gestion.comercial.entity.GastoAdministrativo;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class CotizacionVentaResponse {
    private Long id;

    private String sucursal;

    private Long numeroCotizacion;

    private Integer idVendedor;

    private String patente;

    private String nombreCliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fecha;

    private Double precioBase;

    private Double importeIVA;

    private Double gastosAdministrativos;

    private Boolean garatiaExtendida;

    private Double total;

    private String estadoCotizacion;

    private List<GastoAdministrativo> gastoAdministrativos;

}
