package com.gestion.comercial.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_plan")
    private Integer idPlan;

    @Column(name = "ordinal")
    private Integer ordinal;

    @Column(name = "scoring_asociado")
    private String scoringAsociado;

    @Column(name = "tasa_interes")
    private Double tasaInteres;

    @Column(name = "monto_interes")
    private Double montoInteres;

    @Column(name = "cant_cuotas")
    private Integer cantCuotas;

    @Column(name = "valor_cuota")
    private Double valorCuota;

    @Column(name = "factura_id", length = 8, nullable = false)
    private Long facturaId;

    @OneToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Factura factura;
}
