package com.websystique.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.websystique.springmvc.configuration.RequestIdFilter;
import com.websystique.springmvc.exception.ApiError;
import com.websystique.springmvc.exception.EmployeeNotFoundException;

/**
 * Maps domain exceptions to user-facing views with a typed ApiError payload.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired(required = false)
	private MetricsController metricsController;

	@ExceptionHandler(EmployeeNotFoundException.class)
	public String handleEmployeeNotFound(EmployeeNotFoundException ex, ModelMap model) {
		if (metricsController != null) {
			metricsController.recordError();
		}
		ApiError error = new ApiError("EMPLOYEE_NOT_FOUND", ex.getMessage(),
				MDC.get(RequestIdFilter.REQUEST_ID));
		logger.warn("Handled missing employee code={} requestId={} message={}",
				error.getCode(), error.getRequestId(), error.getMessage());
		model.addAttribute("apiError", error);
		model.addAttribute("success", error.getMessage());
		return "success";
	}

	void setMetricsController(MetricsController metricsController) {
		this.metricsController = metricsController;
	}
}
