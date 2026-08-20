package com.websystique.springmvc.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Liveness probe for operators and container health checks.
 */
@Controller
public class HealthController {

	@Autowired(required = false)
	private MetricsController metricsController;

	@RequestMapping(value = "/health", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> health() {
		if (metricsController != null) {
			metricsController.recordHealthCheck();
		}
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("status", "UP");
		body.put("service", "SpringHibernateExample");
		return body;
	}
}
