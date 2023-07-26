package com.djordjije11.libraryappapi.exception.sort;

import com.djordjije11.libraryappapi.exception.RecordNotValidException;

public class SortQueryNotValidException extends RecordNotValidException {
    public SortQueryNotValidException() {
        super("A sort query is not valid. Valid format Ex. would be: sort_by=desc(last_modified),asc(email)");
    }

    public SortQueryNotValidException(String query) {
        super(String.format("A sort query: %s is not valid. Valid format Ex. would be: sort_by=desc(last_modified),asc(email)", query));
    }
}
