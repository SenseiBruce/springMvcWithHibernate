package com.websystique.springmvc.controller;

import org.springframework.ui.ModelMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.websystique.springmvc.exception.EmployeeNotFoundException;

public class GlobalExceptionHandlerTest {

	@Test
	public void handleEmployeeNotFoundReturnsSuccessView() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ModelMap model = new ModelMap();
		EmployeeNotFoundException ex = new EmployeeNotFoundException(42);

		String view = handler.handleEmployeeNotFound(ex, model);

		Assert.assertEquals(view, "success");
		Assert.assertEquals(model.get("success"), "Employee not found with id: 42");
	}
}
