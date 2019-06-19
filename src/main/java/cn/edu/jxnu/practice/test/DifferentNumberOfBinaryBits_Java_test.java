package cn.edu.jxnu.practice.test;

import cn.edu.jxnu.practice.DifferentNumberOfBinaryBits_Java;
import org.junit.Assert;
import org.junit.Test;

public class DifferentNumberOfBinaryBits_Java_test {

	@Test
	public void testBitSwapRequired() {
		Assert.assertEquals(0,
				DifferentNumberOfBinaryBits_Java.bitSwapRequired(0, 0));
		Assert.assertEquals(4,
				DifferentNumberOfBinaryBits_Java.bitSwapRequired(250, 147));
		Assert.assertEquals(5,
				DifferentNumberOfBinaryBits_Java.bitSwapRequired(-250, -147));
	}
}
