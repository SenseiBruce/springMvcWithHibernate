package com.websystique.springmvc.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Minimal in-process metrics for request counting / operator checks.
 */
@Controller
public class MetricsController {

	private final AtomicLong healthChecks = new AtomicLong();
	private final AtomicLong metricsScrapes = new AtomicLong();
	private final AtomicLong errorsTotal = new AtomicLong();

	public void recordHealthCheck() {
		healthChecks.incrementAndGet();
	}

	public void recordError() {
		errorsTotal.incrementAndGet();
	}

	public long getErrorsTotal() {
		return errorsTotal.get();
	}

	@RequestMapping(value = "/metrics", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> metrics() {
		metricsScrapes.incrementAndGet();
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("health_checks_total", healthChecks.get());
		body.put("metrics_scrapes_total", metricsScrapes.get());
		body.put("errors_total", errorsTotal.get());
		return body;
	}
}
