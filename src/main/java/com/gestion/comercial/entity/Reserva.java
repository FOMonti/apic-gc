package com.gestion.comercial.entity;

import com.gestion.comercial.types.EstadoReserva;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_dni", referencedColumnName = "dni")
    private Cliente cliente;

    @Column(name = "patente", length = 10, nullable = false)
    private String patente;

    @Column(name = "importe")
    private Double importe;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "estado_reserva")
    private EstadoReserva estadoReserva;

    @Column(name = "fencha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "fencha_vencimiento_pago")
    private LocalDate fechaVencimientoPago;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp fecha;
}
