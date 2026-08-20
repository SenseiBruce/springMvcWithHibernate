package com.websystique.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.websystique.springmvc.exception.EmployeeNotFoundException;

/**
 * Maps domain exceptions to user-facing views.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(EmployeeNotFoundException.class)
	public String handleEmployeeNotFound(EmployeeNotFoundException ex, ModelMap model) {
		logger.warn("Handled missing employee: {}", ex.getMessage());
		model.addAttribute("success", ex.getMessage());
		return "success";
	}
}
