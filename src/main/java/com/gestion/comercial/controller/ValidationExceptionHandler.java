package com.gestion.comercial.controller;

import com.gestion.comercial.dto.CustomErrorResponse;
import com.gestion.comercial.dto.ValidationError;
import com.gestion.comercial.exception.EntityNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        CustomErrorResponse response = new CustomErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage("Se produjeron uno o más errores de validación.");
        response.setPath(ex.getNestedPath());

        List<ValidationError> validationErrors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            ValidationError validationError = new ValidationError();
            validationError.setField(fieldError.getField());
            validationError.setMessage(fieldError.getDefaultMessage());
            validationErrors.add(validationError);
        }
        response.setErrors(validationErrors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({EntityNotExistException.class})
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> cotizacionNoExiste(EntityNotExistException ex) {

        CustomErrorResponse response = new CustomErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage(ex.getMessage());
        response.setPath(ex.getPath());
        response.setErrors(new ArrayList<>());

        return ResponseEntity.badRequest().body(response);
    }

}
