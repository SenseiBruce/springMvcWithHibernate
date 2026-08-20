package com.websystique.springmvc.controller;

import java.util.Map;

import org.slf4j.MDC;
import org.springframework.ui.ModelMap;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.websystique.springmvc.configuration.RequestIdFilter;
import com.websystique.springmvc.exception.ApiError;
import com.websystique.springmvc.exception.EmployeeNotFoundException;

public class GlobalExceptionHandlerTest {

	@AfterMethod
	public void clearMdc() {
		MDC.clear();
	}

	@Test
	public void handleEmployeeNotFoundReturnsTypedApiError() {
		MDC.put(RequestIdFilter.REQUEST_ID, "req-42");
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ModelMap model = new ModelMap();
		EmployeeNotFoundException ex = new EmployeeNotFoundException(42);

		String view = handler.handleEmployeeNotFound(ex, model);

		Assert.assertEquals(view, "success");
		Assert.assertEquals(model.get("success"), "Employee not found with id: 42");
		ApiError error = (ApiError) model.get("apiError");
		Assert.assertNotNull(error);
		Assert.assertEquals(error.getCode(), "EMPLOYEE_NOT_FOUND");
		Assert.assertEquals(error.getRequestId(), "req-42");
		Assert.assertEquals(error.getMessage(), "Employee not found with id: 42");
	}

	@Test
	public void handleEmployeeNotFoundIncrementsErrorMetric() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		MetricsController metrics = new MetricsController();
		handler.setMetricsController(metrics);

		handler.handleEmployeeNotFound(new EmployeeNotFoundException(7), new ModelMap());

		Assert.assertEquals(metrics.getErrorsTotal(), 1L);
		Map<String, Object> body = metrics.metrics();
		Assert.assertEquals(body.get("errors_total"), 1L);
	}
}
