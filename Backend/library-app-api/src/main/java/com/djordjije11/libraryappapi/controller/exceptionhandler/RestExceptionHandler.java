package com.djordjije11.libraryappapi.controller.exceptionhandler;

import com.djordjije11.libraryappapi.exception.*;
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
    private static final String RESPONSE_FIELD_ERROR = "error";
    private static final String RESPONSE_FIELD_MESSAGE = "message";
    private static final String RESPONSE_FIELD_MESSAGES = "messages";

    @ExceptionHandler(RequestNotAuthorizedException.class)
    protected ResponseEntity<Object> hanldeRequestNotAuthorized(RequestNotAuthorizedException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.REQUEST_NOT_AUTHORIZED);
        responseBody.put(RESPONSE_FIELD_MESSAGE, ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(RecordNotCurrentVersionException.class)
    protected ResponseEntity<Object> handleRecordNotCurrentVersion(RecordNotCurrentVersionException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_CURRENT_VERSION);
        responseBody.put(RESPONSE_FIELD_MESSAGE, ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(StaleObjectStateException.class)
    protected ResponseEntity<Object> handleRecordNotCurrentVersion(StaleObjectStateException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_CURRENT_VERSION);
        responseBody.put(RESPONSE_FIELD_MESSAGE, ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_FOUND);
        responseBody.put(RESPONSE_FIELD_MESSAGE, ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RequestNotValidException.class)
    protected ResponseEntity<Object> handleRequestNotValid(RequestNotValidException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.REQUEST_NOT_VALID);
        responseBody.put(RESPONSE_FIELD_MESSAGE, ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({RecordNotValidException.class})
    protected ResponseEntity<Object> handleRecordNotValid(RecordNotValidException ex, WebRequest request) {
        var responseBody = new HashMap<String, Object>();
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_VALID);
        responseBody.put(RESPONSE_FIELD_MESSAGE, ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
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
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_VALID);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
}
