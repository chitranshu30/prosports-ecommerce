package com.prosports.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler class that handles exceptions thrown in the application
 * and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles HttpMessageNotReadableException when the incoming request body 
     * cannot be read (e.g., malformed JSON).
     * 
     * @param e the exception thrown
     * @return a ResponseEntity with a bad request status and the error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        // Returning the exception message with a BAD_REQUEST (400) status
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException which occurs when the method argument
     * validation fails (e.g., validation annotations like @NotNull or @Size are violated).
     * 
     * @param e the exception thrown
     * @return a ResponseEntity containing a map of field errors with BAD_REQUEST (400) status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        
        // Initialize a map to hold field names and their respective validation error messages
        Map<String, String> errorMap = new HashMap<>();
        
        // Iterating over the field errors to populate the error map
        e.getBindingResult().getFieldErrors().stream().forEach(error -> {
            String fieldName = error.getField(); // Get the field name that caused the validation error
            String message = error.getDefaultMessage(); // Get the validation error message
            errorMap.put(fieldName, message); // Add the field and its error message to the map
        });
        
        // Return the map of validation errors with a BAD_REQUEST (400) status
        return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserNotFoundException which is thrown when a user is not found in the system.
     * 
     * @param e the exception thrown
     * @return a ResponseEntity with NOT_FOUND (404) status and the exception message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        // Return the error message with a NOT_FOUND (404) status
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UserAlreadyExistsException which is thrown when an attempt is made to create
     * a user that already exists in the system.
     * 
     * @param e the exception thrown
     * @return a ResponseEntity with BAD_REQUEST (400) status and the exception message
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        // Return the error message with a BAD_REQUEST (400) status
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidUsernameOrPassword exception, which is thrown when the username
     * or password provided for authentication is invalid.
     * 
     * @param e the exception thrown
     * @return a ResponseEntity with BAD_REQUEST (400) status and the exception message
     */
    @ExceptionHandler(InvalidUsernameOrPassword.class)
    public ResponseEntity<String> handleInvalidUsernameOrPassword(InvalidUsernameOrPassword e) {
        // Return the error message with a BAD_REQUEST (400) status
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
