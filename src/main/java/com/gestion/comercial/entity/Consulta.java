package com.gestion.comercial.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", length = 15, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 15)
    private String apellido;

    @Column(name = "numero_telefono", length = 15)
    private String numTelefono;

    @Column(name = "email")
    private String email;

    @Column(name = "mensaje")
    private String mensaje;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private Timestamp fecha;
}
