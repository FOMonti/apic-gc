package com.gestion.comercial.dto;

import lombok.Data;

@Data
public class MovimientoRequest {

    private String codigo_unico;

    private Double monto;

    private String concepto;

    private String nombre_completo;

    private Integer documento;
}
