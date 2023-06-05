package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data

public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    private List<ValidationError> errors;

}