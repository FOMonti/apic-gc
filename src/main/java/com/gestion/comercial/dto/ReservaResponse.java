package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gestion.comercial.types.EstadoReserva;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class ReservaResponse {
    private Long id;

    private String patente;

    private Double importe;

    private EstadoReserva estadoReserva;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaVencimiento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaVencimientoPago;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fechaCreacion;

    private ClienteRequest clienteRequest;
}
