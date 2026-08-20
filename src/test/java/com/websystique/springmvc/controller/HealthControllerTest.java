package com.websystique.springmvc.controller;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HealthControllerTest {

	@Test
	public void healthReturnsUpStatus() {
		HealthController controller = new HealthController();
		Map<String, Object> body = controller.health();

		Assert.assertEquals(body.get("status"), "UP");
		Assert.assertEquals(body.get("service"), "SpringHibernateExample");
		Assert.assertEquals(body.size(), 2);
	}
}
