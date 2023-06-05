package com.gestion.comercial.dto;

import lombok.Data;

@Data
public class VehicleResponse {
    private Vehicle result;
    private ErrorData error;
}
