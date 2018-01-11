package com.zilker.exceptionhandling;

public class InvalidUserNameException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidUserNameException(String s) {
		super(s);
	}
}
