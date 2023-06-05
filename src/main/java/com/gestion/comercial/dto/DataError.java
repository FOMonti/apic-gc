package com.gestion.comercial.dto;

import lombok.Data;

@Data
public class DataError {
    private String code;
    private String name;
    private String value;
    private String message;
}
