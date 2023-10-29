package com.example.collections_backend.exception_handling;

import com.example.collections_backend.exception_handling.exceptions.*;
import com.example.collections_backend.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MExceptionHandling {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); //401
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

    @ExceptionHandler(FileDeleteFailedException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest() {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Deleting file failed"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler(SomethingAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleExistException(SomethingAlreadyExist ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); //404
    }

    @ExceptionHandler(PasswordDoesntMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException() {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid Old Password"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); //401
    }

    @ExceptionHandler(SomethingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(SomethingNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); //400
    }

}
