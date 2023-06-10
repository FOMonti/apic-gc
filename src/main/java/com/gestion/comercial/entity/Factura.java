package com.gestion.comercial.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gestion.comercial.types.EstadoFactura;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "factura")
@Data
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sucursal", length = 4, nullable = false)
    private String sucursal;

    @Column(name = "numero_factura", nullable = false)
    private Long numeroFactura;

    @Column(name = "id_vendedor", length = 5, nullable = false)
    private Integer idVendedor;

    @Column(name = "patente", length = 10, nullable = false)
    private String patente;

    @Column(name = "cotizacion_id", length = 8, nullable = false)
    private Long cotizacionID;

    @OneToOne
    @JoinColumn(name = "cotizacion_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CotizacionVenta cotizacion;

    @Column(name = "cliente_dni", length = 8, nullable = false)
    private String clienteDni;

    @ManyToOne
    @JoinColumn(name = "cliente_dni", referencedColumnName = "dni", insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "reserva_id", length = 8)
    private Long reservaId;

    @OneToOne
    @JoinColumn(name = "reserva_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Reserva reserva;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "estado")
    private EstadoFactura estado;

    @Column(name = "garantia_extendida")
    private Boolean garantiaExtendida;

    @Column(name = "garantia_anulada")
    private Boolean garantiaAnulada;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp fechaCreacion;

    @Column(name = "fecha_pago")
    private Timestamp fechaPago;
}
