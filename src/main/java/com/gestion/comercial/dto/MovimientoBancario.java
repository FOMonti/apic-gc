package com.gestion.comercial.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovimientoBancario {

    private int id;
    private LocalDateTime fecha;
    private String id_cuenta_origen;
    private String id_cuenta_destino;
    private double monto;
    private String concepto;
    private String numero_operacion;
    private String tipo;
    private String nombre_persona;
    private String documento_persona;
}
