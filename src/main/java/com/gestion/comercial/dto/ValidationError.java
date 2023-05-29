package com.gestion.comercial.dto;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String message;
}
