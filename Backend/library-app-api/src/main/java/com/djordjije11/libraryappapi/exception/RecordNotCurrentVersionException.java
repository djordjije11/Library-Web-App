package com.djordjije11.libraryappapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordNotCurrentVersionException extends RuntimeException {
    private Object record;

    public RecordNotCurrentVersionException(){
        super("Version of resource is not up to date.");
    }
    public RecordNotCurrentVersionException(Object record){
        this();
        this.record = record;
    }
    public RecordNotCurrentVersionException(String message) {
        super(message);
    }
    public RecordNotCurrentVersionException(String message, Object record){
        this(message);
        this.record = record;
    }

    public Object getRecord(){
        return record;
    }
}
