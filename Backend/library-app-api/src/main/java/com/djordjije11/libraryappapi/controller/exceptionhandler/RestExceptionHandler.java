package com.djordjije11.libraryappapi.controller.exceptionhandler;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotCurrentVersionException.class)
    protected ResponseEntity<Object> handleRecordNotCurrentVersion(RecordNotCurrentVersionException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put("error", ex.getMessage());
        responseBody.put("record", ex.getRecord());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(StaleObjectStateException.class)
    protected ResponseEntity<Object> handleRecordNotCurrentVersion(StaleObjectStateException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put("error", ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put("error", ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RequestNotValidException.class)
    protected ResponseEntity<Object> handleRequestNotValid(RequestNotValidException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put("error", ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        responseBody.put("errors", errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
}
