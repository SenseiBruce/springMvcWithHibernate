package com.websystique.springmvc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.websystique.springmvc.model.Employee;
import com.websystique.springmvc.service.EmployeeService;
import com.websystique.springmvc.service.EmployeeValidationService;
import com.websystique.springmvc.service.ValidationResult;

public class AppControllerTest {

	@Mock
	EmployeeService service;

	@Mock
	EmployeeValidationService validationService;

	@InjectMocks
	AppController appController;

	@Spy
	List<Employee> employees = new ArrayList<Employee>();

	@Spy
	ModelMap model;

	@Mock
	BindingResult result;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		employees = getEmployeeList();
	}

	@Test
	public void listEmployees() {
		when(service.findAllEmployees()).thenReturn(employees);
		Assert.assertEquals(appController.listEmployees(model), "allemployees");
		Assert.assertEquals(model.get("employees"), employees);
		verify(service, atLeastOnce()).findAllEmployees();
	}

	@Test
	public void newEmployee() {
		Assert.assertEquals(appController.newEmployee(model), "registration");
		Assert.assertNotNull(model.get("employee"));
		Assert.assertFalse((Boolean) model.get("edit"));
		Assert.assertEquals(((Employee) model.get("employee")).getId(), 0);
	}

	@Test
	public void saveEmployeeWithValidationError() {
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).saveEmployee(any(Employee.class));
		Assert.assertEquals(appController.saveEmployee(employees.get(0), result, model), "registration");
	}

	@Test
	public void saveEmployeeWithInvalidSsnFormat() {
		when(result.hasErrors()).thenReturn(false);
		when(validationService.validateForWrite(any(Employee.class)))
				.thenReturn(ValidationResult.error("ssn", "SSN_FORMAT", "SSN format is invalid"));
		Assert.assertEquals(appController.saveEmployee(employees.get(0), result, model), "registration");
		Assert.assertEquals(model.get("apiError"), "SSN_FORMAT");
	}

	@Test
	public void saveEmployeeWithValidationErrorNonUniqueSSN() {
		when(result.hasErrors()).thenReturn(false);
		when(validationService.validateForWrite(any(Employee.class)))
				.thenReturn(ValidationResult.error("ssn", "SSN_DUPLICATE", "non unique"));
		Assert.assertEquals(appController.saveEmployee(employees.get(0), result, model), "registration");
		Assert.assertEquals(model.get("apiError"), "SSN_DUPLICATE");
	}

	@Test
	public void saveEmployeeWithSuccess() {
		when(result.hasErrors()).thenReturn(false);
		when(validationService.validateForWrite(any(Employee.class))).thenReturn(ValidationResult.ok());
		doNothing().when(service).saveEmployee(any(Employee.class));
		Assert.assertEquals(appController.saveEmployee(employees.get(0), result, model), "success");
		Assert.assertEquals(model.get("success"), "Employee Axel registered successfully");
	}

	@Test
	public void editEmployee() {
		Employee emp = employees.get(0);
		when(service.findEmployeeBySsn(anyString())).thenReturn(emp);
		Assert.assertEquals(appController.editEmployee("XXX111", model), "registration");
		Assert.assertNotNull(model.get("employee"));
		Assert.assertTrue((Boolean) model.get("edit"));
		Assert.assertEquals(((Employee) model.get("employee")).getId(), 1);
	}

	@Test
	public void updateEmployeeWithValidationError() {
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).updateEmployee(any(Employee.class));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, ""), "registration");
	}

	@Test
	public void updateEmployeeWithValidationErrorNonUniqueSSN() {
		when(result.hasErrors()).thenReturn(false);
		when(validationService.validateForWrite(any(Employee.class)))
				.thenReturn(ValidationResult.error("ssn", "SSN_DUPLICATE", "non unique"));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, ""), "registration");
		Assert.assertEquals(model.get("apiError"), "SSN_DUPLICATE");
	}

	@Test
	public void updateEmployeeWithSuccess() {
		when(result.hasErrors()).thenReturn(false);
		when(validationService.validateForWrite(any(Employee.class))).thenReturn(ValidationResult.ok());
		doNothing().when(service).updateEmployee(any(Employee.class));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, ""), "success");
		Assert.assertEquals(model.get("success"), "Employee Axel updated successfully");
	}

	@Test
	public void deleteEmployee() {
		doNothing().when(service).deleteEmployeeBySsn(anyString());
		Assert.assertEquals(appController.deleteEmployee("123"), "redirect:/list");
	}

	public List<Employee> getEmployeeList() {
		Employee e1 = new Employee();
		e1.setId(1);
		e1.setName("Axel");
		e1.setJoiningDate(new LocalDate());
		e1.setSalary(new BigDecimal(10000));
		e1.setSsn("XXX111");

		Employee e2 = new Employee();
		e2.setId(2);
		e2.setName("Jeremy");
		e2.setJoiningDate(new LocalDate());
		e2.setSalary(new BigDecimal(20000));
		e2.setSsn("XXX222");

		employees.add(e1);
		employees.add(e2);
		return employees;
	}
}
