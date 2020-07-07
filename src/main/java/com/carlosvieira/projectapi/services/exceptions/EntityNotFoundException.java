package com.carlosvieira.projectapi.services.exceptions;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
		super(message);
	}
	
	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
