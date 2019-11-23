package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.StringDisplacement;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class StringDisplacement_test {

	@Test
	public void testStringDisplacement2() {
		Assert.assertFalse(StringDisplacement.stringDisplacement2(null, ""));
		Assert.assertFalse(StringDisplacement.stringDisplacement2("", null));
		Assert.assertFalse(
				StringDisplacement.stringDisplacement2("2", "a/b/c"));

		Assert.assertTrue(StringDisplacement.stringDisplacement2("1", "1"));
	}

	@Test
	public void stringDisplacement() {
		Assert.assertFalse(StringDisplacement.stringDisplacement(null, ""));
		Assert.assertFalse(StringDisplacement.stringDisplacement("", null));
		Assert.assertFalse(
				StringDisplacement.stringDisplacement("2", "a/b/c"));

		Assert.assertTrue(StringDisplacement.stringDisplacement("1", "1"));
	}
}
