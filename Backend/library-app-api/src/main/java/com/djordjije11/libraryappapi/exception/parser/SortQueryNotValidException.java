package com.djordjije11.libraryappapi.exception.parser;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class SortQueryNotValidException extends RequestNotValidException {
    public SortQueryNotValidException() {
        super("A sort query is not valid. Valid format Ex. would be: sort_by=desc(last_modified),asc(email)");
    }
    public SortQueryNotValidException(String query) {
        super(String.format("A sort query: %s is not valid. Valid format Ex. would be: sort_by=desc(last_modified),asc(email)", query));
    }
}
