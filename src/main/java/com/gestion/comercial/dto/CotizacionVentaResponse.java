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

    private String estadoCotizacion;

    private Integer idVendedor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fecha;

    private String dni;

    private String patente;

    private Boolean garantiaExtendida;

    private Double precioVenta;

    private Double importeIVA;

    private List<GastoAdministrativo> gastosAdministrativos;

    private Double importeTotalGastosAdministrativos;

    private Double total;
}
