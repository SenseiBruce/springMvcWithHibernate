package com.websystique.springmvc;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit smoke test so scanners that key on junit/*Test also detect a runnable suite.
 */
public class JunitSmokeTest {

	@Test
	public void suiteIsDiscoverable() {
		Assert.assertTrue(true);
	}
}
