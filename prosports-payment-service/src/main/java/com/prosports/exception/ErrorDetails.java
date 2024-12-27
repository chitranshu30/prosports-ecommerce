package com.prosports.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
	private int statusCode;
	private String message;
	private String details;
	private LocalDateTime timestamp;

	// Constructor to ensure timestamp is always set
	public ErrorDetails(int statusCode, String message, String details) {
		this.statusCode = statusCode;
		this.message = message;
		this.details = details;
		this.timestamp = LocalDateTime.now(); // Automatically set the timestamp
	}
 
}
