package com.websystique.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.websystique.springmvc.model.Employee;
import com.websystique.springmvc.service.EmployeeService;
import com.websystique.springmvc.service.EmployeeValidationService;
import com.websystique.springmvc.service.PageResult;
import com.websystique.springmvc.service.ValidationResult;
import com.websystique.springmvc.util.SsnValidator;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	EmployeeService service;

	@Autowired
	EmployeeValidationService validationService;

	/*
	 * This method will list all existing employees.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listEmployees(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, ModelMap model) {

		List<Employee> all = service.findAllEmployees();
		PageResult<Employee> pageResult = PageResult.of(all, page, size);
		model.addAttribute("employees", pageResult.getItems());
		model.addAttribute("page", pageResult.getPage());
		model.addAttribute("size", pageResult.getSize());
		model.addAttribute("total", pageResult.getTotal());
		model.addAttribute("totalPages", pageResult.getTotalPages());
		return "allemployees";
	}

	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(@Valid Employee employee, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		ValidationResult validation = validationService.validateForWrite(employee);
		if (!validation.isValid()) {
			result.addError(new FieldError("employee", validation.getField(), validation.getMessage()));
			model.addAttribute("apiError", validation.getCode());
			return "registration";
		}

		service.saveEmployee(employee);

		model.addAttribute("success",
				"Employee " + employee.getName() + " registered successfully");
		return "success";
	}

	/*
	 * This method will provide the medium to update an existing employee.
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String ssn, ModelMap model) {
		if (!SsnValidator.isWellFormed(ssn)) {
			model.addAttribute("apiError", "SSN_PATH_INVALID");
			model.addAttribute("success", "Invalid SSN path value");
			return "success";
		}
		Employee employee = service.findEmployeeBySsn(ssn);
		model.addAttribute("employee", employee);
		model.addAttribute("edit", true);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * updating employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.POST)
	public String updateEmployee(@Valid Employee employee, BindingResult result,
			ModelMap model, @PathVariable String ssn) {

		if (!SsnValidator.isWellFormed(ssn)) {
			model.addAttribute("apiError", "SSN_PATH_INVALID");
			return "registration";
		}

		if (result.hasErrors()) {
			return "registration";
		}

		ValidationResult validation = validationService.validateForWrite(employee);
		if (!validation.isValid()) {
			result.addError(new FieldError("employee", validation.getField(), validation.getMessage()));
			model.addAttribute("apiError", validation.getCode());
			return "registration";
		}

		service.updateEmployee(employee);

		model.addAttribute("success",
				"Employee " + employee.getName() + " updated successfully");
		return "success";
	}

	/*
	 * This method will delete an employee by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn, ModelMap model) {
		if (!SsnValidator.isWellFormed(ssn)) {
			model.addAttribute("apiError", "SSN_PATH_INVALID");
			model.addAttribute("success", "Invalid SSN path value");
			return "success";
		}
		service.deleteEmployeeBySsn(ssn);
		return "redirect:/list";
	}

}
