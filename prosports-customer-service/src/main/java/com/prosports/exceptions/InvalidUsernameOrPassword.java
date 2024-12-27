package com.prosports.exceptions;

public class InvalidUsernameOrPassword extends RuntimeException {

	public InvalidUsernameOrPassword(String message) {
		super(message);
	}

}
