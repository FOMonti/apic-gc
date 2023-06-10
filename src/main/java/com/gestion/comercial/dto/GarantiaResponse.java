package com.gestion.comercial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class GarantiaResponse {

    private Boolean garantiaExtendida;

    private Boolean garantiaAnulada;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fecha;
}
