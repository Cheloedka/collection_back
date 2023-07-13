package com.example.collections_backend.exception_handling;

import com.example.collections_backend.exception_handling.exceptions.ConformationTokenExpiredException;
import com.example.collections_backend.exception_handling.exceptions.EmailSendFailedException;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.exception_handling.exceptions.UserNotFoundException;
import com.example.collections_backend.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingUser {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); //401
    }

    @ExceptionHandler(BadCredentialsException.class) //BadCredentialsException handler
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
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); //401 //todo what type of error is DisabledException

    }

    @ExceptionHandler(ConformationTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(ConformationTokenExpiredException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler(EmailSendFailedException.class)
    public ResponseEntity<ErrorResponse> handleEmailSendException() {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Failed to send confirmation email"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityException() {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Page not found"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); //404

    }

}
