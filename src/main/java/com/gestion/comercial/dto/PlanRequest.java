package com.gestion.comercial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlanRequest {

    @NotNull(message = "El campo id del plan no puede estar vacio")
    private Integer idPlan;

    @NotNull(message = "El campo ordinal no puede estar vacio")
    private Integer ordinal;

    @Size(min = 4, max = 7, message = "El scoring asociado tiene un tamaño minimo de {min} y maximo de {max} caracteres")
    private String scoringAsociado;

    @NotNull(message = "El campo valor de cuota no puede estar vacio")
    private Double tasaInteres;

    @NotNull(message = "El campo monto con interes no puede estar vacio")
    private Double montoConInteres;

    @NotNull(message = "El cantidad de cuotas no puede estar vacio")
    @Min(value = 1 , message = "La cantidad de cuotas no puede ser 0 ni negativo")
    private Integer cantCuotas;

    @NotNull(message = "El campo valor de cuota no puede estar vacio")
    private Double valorCuota;
}
