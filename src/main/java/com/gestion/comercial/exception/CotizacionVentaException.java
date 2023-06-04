package com.gestion.comercial.exception;

public class CotizacionVentaException extends RuntimeException{

    private static final String ERROR_TYPE = "Error (400) Bad Request Exception.";

    private final String PATH;

    public CotizacionVentaException(String errorDetails, String path) {
        super(ERROR_TYPE + " " + errorDetails);
        this.PATH = path;
    }

    public String getPath(){
        return PATH;
    }
}
