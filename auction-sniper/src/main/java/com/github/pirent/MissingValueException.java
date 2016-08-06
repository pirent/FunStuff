package com.github.pirent;

public class MissingValueException extends RuntimeException {

	public MissingValueException(String fieldName) {
		super(fieldName);
	}

	private static final long serialVersionUID = 1L;

}
