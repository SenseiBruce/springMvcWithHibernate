package com.websystique.springmvc.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.websystique.springmvc.model.Employee;
import com.websystique.springmvc.util.SsnValidator;

/**
 * Centralizes employee SSN format and uniqueness checks for controllers.
 */
@Service
public class EmployeeValidationService {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private MessageSource messageSource;

	public ValidationResult validateForWrite(Employee employee) {
		if (!SsnValidator.isWellFormed(employee.getSsn())) {
			return ValidationResult.error("ssn", "SSN_FORMAT", "SSN format is invalid");
		}
		if (!employeeService.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
			String message = messageSource.getMessage("non.unique.ssn",
					new String[] { employee.getSsn() }, Locale.getDefault());
			return ValidationResult.error("ssn", "SSN_DUPLICATE", message);
		}
		return ValidationResult.ok();
	}
}
