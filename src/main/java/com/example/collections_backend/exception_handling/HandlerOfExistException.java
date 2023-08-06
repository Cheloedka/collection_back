package com.example.collections_backend.exception_handling;

import com.example.collections_backend.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class HandlerOfExistException {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handlePasswordNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Password is wrong"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); //401
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleUserIsDisabledException() {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "User is disabled"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); //401

    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "File is not found"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); //400
    }

}
