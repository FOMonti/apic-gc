package com.gestion.comercial.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data

public class CustomErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<ValidationError> errors;

}