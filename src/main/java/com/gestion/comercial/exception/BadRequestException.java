package com.gestion.comercial.exception;

public class BadRequestException  extends RuntimeException{

    private static final String ERROR_TYPE = "Error (400) Bad Request Exception.";

    public BadRequestException(String errorDetails) {
        super(ERROR_TYPE + " " + errorDetails);
    }
}