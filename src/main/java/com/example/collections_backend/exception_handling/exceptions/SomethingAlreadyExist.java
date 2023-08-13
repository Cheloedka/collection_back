package com.example.collections_backend.exception_handling.exceptions;

public class SomethingAlreadyExist extends RuntimeException{
    public SomethingAlreadyExist(String message) {
        super(message);
    }
}
