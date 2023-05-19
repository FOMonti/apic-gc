package com.gestion.comercial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion.comercial.types.CostoAdministrativo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gasto_administrativo")
public class GastoAdministrativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cotizacion_venta_id")
    private CotizacionVenta cotizacionVenta;

    @Column(name = "linea")
    private Integer linea;

    @Column(name = "nombre")
    @Enumerated(EnumType.STRING)
    private CostoAdministrativo nombre;

    @Column(name = "importe")
    private Double importe;
}
