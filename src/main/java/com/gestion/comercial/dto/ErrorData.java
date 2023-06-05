package com.gestion.comercial.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ErrorData {
    private String errorCode;
    private String messages;
    private List<DataError> dataErrors;
    private Map<String, Object> attributes;
}
