package com.gestion.comercial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Auto")
public class Auto {
    @Id
    private String patente;
}
