package com.websystique.springmvc.service;

/**
 * Typed validation outcome used by controllers instead of ad-hoc FieldError building.
 */
public final class ValidationResult {

	private final boolean valid;
	private final String field;
	private final String code;
	private final String message;

	private ValidationResult(boolean valid, String field, String code, String message) {
		this.valid = valid;
		this.field = field;
		this.code = code;
		this.message = message;
	}

	public static ValidationResult ok() {
		return new ValidationResult(true, null, null, null);
	}

	public static ValidationResult error(String field, String code, String message) {
		return new ValidationResult(false, field, code, message);
	}

	public boolean isValid() {
		return valid;
	}

	public String getField() {
		return field;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
