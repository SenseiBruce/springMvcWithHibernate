package com.websystique.springmvc.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SsnValidatorTest {

	@Test
	public void acceptsAlphanumericWithinBounds() {
		Assert.assertTrue(SsnValidator.isWellFormed("XXX111"));
		Assert.assertTrue(SsnValidator.isWellFormed("12345"));
	}

	@Test
	public void rejectsNullBlankAndInvalidShapes() {
		Assert.assertFalse(SsnValidator.isWellFormed(null));
		Assert.assertFalse(SsnValidator.isWellFormed(""));
		Assert.assertFalse(SsnValidator.isWellFormed("ab"));
		Assert.assertFalse(SsnValidator.isWellFormed("bad ssn!"));
	}
}
