package com.websystique.springmvc.exception;

/**
 * Thrown when an employee cannot be found for an update or lookup operation.
 */
public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(int id) {
		super("Employee not found with id: " + id);
	}

	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
