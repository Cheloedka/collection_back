package com.example.collections_backend.exception_handling.exceptions;

public class ConformationTokenExpiredException extends RuntimeException {

    public ConformationTokenExpiredException(String message) {
        super(message);
    }

}
