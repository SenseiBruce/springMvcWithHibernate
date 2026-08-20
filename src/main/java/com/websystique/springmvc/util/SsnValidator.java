package com.websystique.springmvc.util;

/**
 * Lightweight SSN shape checks used by registration flows.
 */
public final class SsnValidator {

	private SsnValidator() {
	}

	public static boolean isWellFormed(String ssn) {
		if (ssn == null) {
			return false;
		}
		String trimmed = ssn.trim();
		return trimmed.length() >= 5 && trimmed.length() <= 12
				&& trimmed.matches("[A-Za-z0-9]+");
	}
}
