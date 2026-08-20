package com.websystique.springmvc.controller;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MetricsControllerTest {

	@Test
	public void metricsReturnsCounters() {
		MetricsController controller = new MetricsController();
		controller.recordHealthCheck();
		controller.recordHealthCheck();
		controller.recordError();

		Map<String, Object> body = controller.metrics();

		Assert.assertEquals(body.get("health_checks_total"), 2L);
		Assert.assertEquals(body.get("metrics_scrapes_total"), 1L);
		Assert.assertEquals(body.get("errors_total"), 1L);
	}

	@Test
	public void metricsScrapesIncrementOnEachCall() {
		MetricsController controller = new MetricsController();
		controller.metrics();
		Map<String, Object> body = controller.metrics();
		Assert.assertEquals(body.get("metrics_scrapes_total"), 2L);
	}
}
