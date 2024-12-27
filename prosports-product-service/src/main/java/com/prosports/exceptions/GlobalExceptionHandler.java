package com.prosports.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CategoryAlreadyExistsException.class)
	public ResponseEntity<String> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ProductTypeNotFoundException.class)
	public ResponseEntity<String> handleProductTypeNotFoundException(ProductTypeNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductVariantNotFoundException.class)
	public ResponseEntity<String> handleProductVariantNotFoundException(ProductVariantNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductAlreadyExistException.class)
	public ResponseEntity<String> handleProductAlreadyExistException(ProductAlreadyExistException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ProductTypeAlreadyExistException.class)
	public ResponseEntity<String> handleProductTypeAlreadyExistException(ProductTypeAlreadyExistException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
	}
}
