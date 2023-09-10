package com.example.collections_backend.exception_handling.exceptions;

public class SomethingNotFoundException extends RuntimeException{

    public SomethingNotFoundException(String message) {
        super(message);
    }
}
