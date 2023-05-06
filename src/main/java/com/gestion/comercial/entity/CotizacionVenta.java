package com.gestion.comercial.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Table(name = "cotizacion_venta")
@Entity
@Data
public class CotizacionVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sucursal", length = 5, nullable = false)
    private String sucursal;

    @Column(name = "numero_cotizacion", nullable = false)
    private Long numeroCotizacion;

    @Column(name = "id_vendedor", nullable = false)
    private Integer idVendedor;

    @Column(name = "patente", length = 7, nullable = false)
    private String patente;

    @Column(name = "dni_cliente", length = 10, nullable = false)
    private String dniCliente;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private Timestamp fecha;

    @Column(name = "precio_base")
    private Double precioBase;

    @Column(name = "importe_iva")
    private Double importeIVA;

    @Column(name = "gasto_traslado")
    private Double precioTraslado;

    @Column(name = "gastos_administrativos")
    private Double gastosAdministrativos;

    @Column(name = "gastos_garantia")
    private Double gastosGarantia;

    @Column(name = "garantia_extendida")
    private Boolean garatiaExtendida;

    @Column(name = "total")
    private Double total;

}
