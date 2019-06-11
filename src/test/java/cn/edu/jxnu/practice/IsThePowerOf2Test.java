package cn.edu.jxnu.practice;

import org.junit.Assert;
import org.junit.Test;

public class IsThePowerOf2Test {

	@Test
	public void testPowerOf2() {
		Assert.assertEquals(-1, IsThePowerOf2.powerOf2(5L));
		Assert.assertEquals(0, IsThePowerOf2.powerOf2(1L));
		Assert.assertEquals(1, IsThePowerOf2.powerOf2(2L));
	}
}
