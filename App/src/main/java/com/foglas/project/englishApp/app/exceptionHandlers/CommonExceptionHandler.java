package com.foglas.project.englishApp.app.exceptionHandlers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;

@RestControllerAdvice
public class CommonExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationViolationHandler(MethodArgumentNotValidException ex){
        StringBuilder error = new StringBuilder();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError concreteError : fieldErrors) {
           error.append("  " + concreteError.getField() + " : " + concreteError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(error);
    }
}
