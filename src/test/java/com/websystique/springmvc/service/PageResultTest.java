package com.websystique.springmvc.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.websystique.springmvc.model.Employee;

public class PageResultTest {

	@Test
	public void slicesFirstPage() {
		List<Employee> all = sampleEmployees();
		PageResult<Employee> page = PageResult.of(all, 0, 1);
		Assert.assertEquals(page.getItems().size(), 1);
		Assert.assertEquals(page.getItems().get(0).getName(), "Axel");
		Assert.assertEquals(page.getTotal(), 2);
		Assert.assertEquals(page.getTotalPages(), 2);
	}

	@Test
	public void slicesSecondPage() {
		List<Employee> all = sampleEmployees();
		PageResult<Employee> page = PageResult.of(all, 1, 1);
		Assert.assertEquals(page.getItems().get(0).getName(), "Jeremy");
	}

	@Test
	public void emptyWhenPagePastEnd() {
		PageResult<Employee> page = PageResult.of(sampleEmployees(), 5, 10);
		Assert.assertTrue(page.getItems().isEmpty());
		Assert.assertEquals(page.getTotal(), 2);
	}

	private List<Employee> sampleEmployees() {
		Employee e1 = new Employee();
		e1.setName("Axel");
		e1.setSsn("XXX111");
		e1.setJoiningDate(new LocalDate());
		e1.setSalary(new BigDecimal("10000"));
		Employee e2 = new Employee();
		e2.setName("Jeremy");
		e2.setSsn("XXX222");
		e2.setJoiningDate(new LocalDate());
		e2.setSalary(new BigDecimal("20000"));
		return Arrays.asList(e1, e2);
	}
}
