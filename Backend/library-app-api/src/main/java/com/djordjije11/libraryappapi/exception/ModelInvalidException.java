package com.djordjije11.libraryappapi.exception;

public class ModelInvalidException extends RuntimeException {
    public ModelInvalidException() {
        super("Model is invalid.");
    }

    public <T> ModelInvalidException(Class<T> modelClass, String message) {
        super(String.format("Model type of %s is invalid. %s", modelClass, message));
    }
}
