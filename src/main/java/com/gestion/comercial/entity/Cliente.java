package com.gestion.comercial.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "dni", length = 8)
    private String dni;

    @Column(name = "nombre", length = 15, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 15)
    private String apellido;

    @Column(name = "numero_telefono", length = 15)
    private String numTelefono;

    @Column(name = "email", length = 70)
    private String email;

    @Column(name = "direccion", length = 20)
    private String direccion;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private Timestamp fecha;
}
