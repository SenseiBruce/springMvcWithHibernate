package com.websystique.springmvc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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
import org.testng.annotations.BeforeMethod;
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

	ModelMap model = new ModelMap();

	@Mock
	BindingResult result;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		employees = new ArrayList<Employee>();
		employees = getEmployeeList();
		model = new ModelMap();
	}

	@Test
	public void listEmployees() {
		when(service.findAllEmployees()).thenReturn(employees);
		Assert.assertEquals(appController.listEmployees(0, 10, model), "allemployees");
		Assert.assertEquals(model.get("employees"), employees);
		Assert.assertEquals(model.get("total"), 2);
		verify(service, atLeastOnce()).findAllEmployees();
	}

	@Test
	public void listEmployeesPaginatesResults() {
		when(service.findAllEmployees()).thenReturn(employees);
		Assert.assertEquals(appController.listEmployees(0, 1, model), "allemployees");
		@SuppressWarnings("unchecked")
		List<Employee> page = (List<Employee>) model.get("employees");
		Assert.assertEquals(page.size(), 1);
		Assert.assertEquals(model.get("totalPages"), 2);
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
	public void saveEmployeeRejectsWhenBindingResultHasBeanValidationErrors() {
		Employee invalid = new Employee();
		invalid.setName("Al");
		invalid.setSsn("");
		invalid.setSalary(null);
		when(result.hasErrors()).thenReturn(true);

		String view = appController.saveEmployee(invalid, result, model);

		Assert.assertEquals(view, "registration");
		verify(service, never()).saveEmployee(any(Employee.class));
	}

	@Test
	public void updateEmployeeRejectsWhenBindingResultHasBeanValidationErrors() {
		Employee invalid = new Employee();
		invalid.setId(1);
		invalid.setName("x");
		invalid.setSsn("!!");
		when(result.hasErrors()).thenReturn(true);

		String view = appController.updateEmployee(invalid, result, model, "!!");

		Assert.assertEquals(view, "registration");
		verify(service, never()).updateEmployee(any(Employee.class));
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
	public void editEmployeeRejectsMalformedSsnPath() {
		Assert.assertEquals(appController.editEmployee("!!", model), "success");
		Assert.assertEquals(model.get("apiError"), "SSN_PATH_INVALID");
		verify(service, never()).findEmployeeBySsn(anyString());
	}

	@Test
	public void updateEmployeeWithValidationError() {
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).updateEmployee(any(Employee.class));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, "XXX111"),
				"registration");
	}

	@Test
	public void updateEmployeeWithValidationErrorNonUniqueSSN() {
		when(result.hasErrors()).thenReturn(false);
		when(validationService.validateForWrite(any(Employee.class)))
				.thenReturn(ValidationResult.error("ssn", "SSN_DUPLICATE", "non unique"));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, "XXX111"),
				"registration");
		Assert.assertEquals(model.get("apiError"), "SSN_DUPLICATE");
	}

	@Test
	public void updateEmployeeWithSuccess() {
		when(result.hasErrors()).thenReturn(false);
		when(validationService.validateForWrite(any(Employee.class))).thenReturn(ValidationResult.ok());
		doNothing().when(service).updateEmployee(any(Employee.class));
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, "XXX111"), "success");
		Assert.assertEquals(model.get("success"), "Employee Axel updated successfully");
	}

	@Test
	public void updateEmployeeRejectsMalformedSsnPath() {
		when(result.hasErrors()).thenReturn(false);
		Assert.assertEquals(appController.updateEmployee(employees.get(0), result, model, "!!"),
				"registration");
		Assert.assertEquals(model.get("apiError"), "SSN_PATH_INVALID");
		verify(service, never()).updateEmployee(any(Employee.class));
	}

	@Test
	public void deleteEmployee() {
		doNothing().when(service).deleteEmployeeBySsn(anyString());
		Assert.assertEquals(appController.deleteEmployee("XXX111", model), "redirect:/list");
	}

	@Test
	public void deleteEmployeeRejectsMalformedSsnPath() {
		Assert.assertEquals(appController.deleteEmployee("!!", model), "success");
		Assert.assertEquals(model.get("apiError"), "SSN_PATH_INVALID");
		verify(service, never()).deleteEmployeeBySsn(anyString());
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
