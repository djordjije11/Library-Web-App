package com.djordjije11.libraryappapi.exception.sort;

import com.djordjije11.libraryappapi.exception.RecordNotValidException;

public class SortPropertyNotValidException extends RecordNotValidException {
    private String property;

    public SortPropertyNotValidException() {
        super("A sort query is not valid. There is a sort property in query that cannot be sorted by.");
    }

    public SortPropertyNotValidException(String property){
        super(String.format("A sort query is not valid. A sort property: %s cannot be sorted by.", property));
        this.property = property;
    }

    public SortPropertyNotValidException(String query, String property){
        super(String.format("A sort query: %s is not valid. A sort property: %s cannot be sorted by.", query, property));
        this.property = property;
    }

    public String getProperty(){
        return property;
    }
}
