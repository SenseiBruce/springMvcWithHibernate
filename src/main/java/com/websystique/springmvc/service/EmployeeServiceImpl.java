package com.websystique.springmvc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.EmployeeDao;
import com.websystique.springmvc.exception.EmployeeNotFoundException;
import com.websystique.springmvc.model.Employee;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeDao dao;

	public Employee findById(int id) {
		return dao.findById(id);
	}

	public void saveEmployee(Employee employee) {
		logger.info("Saving employee with ssn={}", employee.getSsn());
		dao.saveEmployee(employee);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends.
	 */
	public void updateEmployee(Employee employee) {
		Employee entity = dao.findById(employee.getId());
		if (entity == null) {
			logger.warn("Attempted to update missing employee id={}", employee.getId());
			throw new EmployeeNotFoundException(employee.getId());
		}
		entity.setName(employee.getName());
		entity.setJoiningDate(employee.getJoiningDate());
		entity.setSalary(employee.getSalary());
		entity.setSsn(employee.getSsn());
		logger.info("Updated employee id={}", employee.getId());
	}

	public void deleteEmployeeBySsn(String ssn) {
		logger.info("Deleting employee with ssn={}", ssn);
		dao.deleteEmployeeBySsn(ssn);
	}

	public List<Employee> findAllEmployees() {
		return dao.findAllEmployees();
	}

	public Employee findEmployeeBySsn(String ssn) {
		return dao.findEmployeeBySsn(ssn);
	}

	public boolean isEmployeeSsnUnique(Integer id, String ssn) {
		Employee employee = findEmployeeBySsn(ssn);
		return (employee == null || ((id != null) && (employee.getId() == id)));
	}

}
