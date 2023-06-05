package com.gestion.comercial.dto;

import lombok.Data;

@Data
public class Vehicle {
    private String plate;
    private String dni;
    private Double purchasePrice;
    private Double sellPrice;
    private String status;
    private Double score;
    private Double repairCost;
    private String branch;
    private String kilometers;
    private String message;
    private String brand;
    private String model;
    private boolean gnc;
    private String fuelType;
    private String engine;
    private String origin;
    private String year;
    private Double basePrice;
    private Double debt;
    private String vpa;
    private String rva;
    private String vtv;
    private String picture1;
    private String picture2;
    private String picture3;
}
