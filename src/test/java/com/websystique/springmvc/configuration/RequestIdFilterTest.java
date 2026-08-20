package com.websystique.springmvc.configuration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class RequestIdFilterTest {

	@AfterMethod
	public void clearMdc() {
		MDC.clear();
	}

	@Test
	public void usesIncomingRequestIdHeader() throws Exception {
		RequestIdFilter filter = new RequestIdFilter();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		when(request.getHeader("X-Request-Id")).thenReturn("abc-123");

		filter.doFilter(request, response, chain);

		verify(response).setHeader("X-Request-Id", "abc-123");
		verify(chain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
		Assert.assertNull(MDC.get(RequestIdFilter.REQUEST_ID));
	}

	@Test
	public void generatesRequestIdWhenHeaderMissing() throws Exception {
		RequestIdFilter filter = new RequestIdFilter();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		when(request.getHeader("X-Request-Id")).thenReturn(null);

		filter.doFilter(request, response, chain);

		verify(response).setHeader(org.mockito.ArgumentMatchers.eq("X-Request-Id"),
				org.mockito.ArgumentMatchers.argThat(id -> id != null && id.length() > 8));
	}
}
