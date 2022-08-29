package com.company.app.exception;

@SuppressWarnings("serial")
public class WrongCredentialException extends Exception {

	public WrongCredentialException(String message) {
		super(message);
	}

}
