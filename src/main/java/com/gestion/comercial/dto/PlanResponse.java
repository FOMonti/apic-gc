package com.gestion.comercial.dto;

import lombok.Data;

@Data
public class PlanResponse {

    private Long id;

    private Integer idPlan;

    private Integer ordinal;

    private String scoringAsociado;

    private Double tasaInteres;

    private Double montoInteres;

    private Integer cantCuotas;

    private Double valorCuota;
}
