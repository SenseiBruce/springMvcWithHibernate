package com.websystique.springmvc.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.websystique.springmvc.model.Employee;

public class EmployeeValidationServiceTest {

	@Mock
	private EmployeeService employeeService;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private EmployeeValidationService validationService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void rejectsInvalidSsnFormat() {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setSsn("!!");

		ValidationResult result = validationService.validateForWrite(employee);

		Assert.assertFalse(result.isValid());
		Assert.assertEquals(result.getCode(), "SSN_FORMAT");
		Assert.assertEquals(result.getField(), "ssn");
	}

	@Test
	public void rejectsDuplicateSsn() {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setSsn("XXX111");
		when(employeeService.isEmployeeSsnUnique(anyInt(), anyString())).thenReturn(false);
		when(messageSource.getMessage(eq("non.unique.ssn"), any(Object[].class), any(Locale.class)))
				.thenReturn("non unique");

		ValidationResult result = validationService.validateForWrite(employee);

		Assert.assertFalse(result.isValid());
		Assert.assertEquals(result.getCode(), "SSN_DUPLICATE");
	}

	@Test
	public void acceptsValidUniqueSsn() {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setSsn("XXX111");
		when(employeeService.isEmployeeSsnUnique(anyInt(), anyString())).thenReturn(true);

		ValidationResult result = validationService.validateForWrite(employee);

		Assert.assertTrue(result.isValid());
	}
}
