package com.prosports.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		Map<String, String> errorMap = new HashMap<>();
		e.getBindingResult().getFieldErrors().stream().forEach(error -> {
			String fieldName = error.getField();
			String message = error.getDefaultMessage();
			errorMap.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CountryAlreadyExistsException.class)
	public ResponseEntity<String> handleCountryAlreadyExistsException(CountryAlreadyExistsException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(PriceAlreadyExistsException.class)
	public ResponseEntity<String> handlePriceAlreadyExistsException(PriceAlreadyExistsException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
