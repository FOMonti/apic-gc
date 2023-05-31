package com.gestion.comercial.entity;

import com.gestion.comercial.types.EstadoCotizacion;
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

    @Column(name = "sucursal", length = 4, nullable = false)
    private String sucursal;

    @Column(name = "numero_cotizacion", nullable = false)
    private Long numeroCotizacion;

    @Column(name = "id_vendedor", length = 5, nullable = false)
    private Integer idVendedor;

    @Column(name = "patente", length = 10, nullable = false)
    private String patente;

    @Column(name = "email", length = 70, nullable = false)
    private String email;

    @Column(name = "nombre_cliente", length = 20, nullable = false)
    private String nombreCliente;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private Timestamp fecha;

    @Column(name = "precio_base")
    private Double precioBase;

    @Column(name = "importe_iva")
    private Double importeIVA;

    @Column(name = "gastos_administrativos")
    private Double gastosAdministrativos;

    @Column(name = "garantia_extendida")
    private Boolean garatiaExtendida;

    @Column(name = "total")
    private Double total;

    @Column(name = "estado")
    @Enumerated(EnumType.ORDINAL)
    private EstadoCotizacion estadoCotizacion;

}
