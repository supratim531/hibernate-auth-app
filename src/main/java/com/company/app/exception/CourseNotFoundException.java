package com.company.app.exception;

@SuppressWarnings("serial")
public class CourseNotFoundException extends Exception {

	public CourseNotFoundException(String message) {
		super(message);
	}

}
