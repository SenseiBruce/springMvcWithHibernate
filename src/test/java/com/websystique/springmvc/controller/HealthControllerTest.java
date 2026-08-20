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

	@Test
	public void healthRecordsMetricWhenWired() {
		HealthController controller = new HealthController();
		MetricsController metrics = new MetricsController();
		controller = new HealthController();
		// inject via package-visible test approach: use reflection-free setter path
		setMetrics(controller, metrics);
		controller.health();
		Map<String, Object> body = metrics.metrics();
		Assert.assertEquals(body.get("health_checks_total"), 1L);
	}

	private static void setMetrics(HealthController controller, MetricsController metrics) {
		try {
			java.lang.reflect.Field field = HealthController.class.getDeclaredField("metricsController");
			field.setAccessible(true);
			field.set(controller, metrics);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}
}
