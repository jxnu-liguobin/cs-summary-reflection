package cn.edu.jxnu.practice;

import org.junit.Assert;
import org.junit.Test;

public class GetGcdTest {

	@Test
	public void testGcd() {
		Assert.assertEquals(0, GetGcd.gcd(0, 0));
		Assert.assertEquals(4, GetGcd.gcd(8, 20));
		Assert.assertEquals(10, GetGcd.gcd(10, 10));
	}

	@Test
	public void testGcd2() {
		Assert.assertEquals(0, GetGcd.gcd2(0, 0));
		Assert.assertEquals(4, GetGcd.gcd2(8, 20));
		Assert.assertEquals(10, GetGcd.gcd2(10, 10)); 
	}

	@Test
	public void testLcm() {
		Assert.assertEquals(8, GetGcd.lcm(8, 8));
		Assert.assertEquals(40, GetGcd.lcm(20, 8));
		Assert.assertEquals(-1, GetGcd.lcm(1_003_094_561, 1_003_094_561));
	}
	
	@Test 
	public void testGcd3() {
		Assert.assertEquals(0, GetGcd.gcd3(0, 0)); 
		Assert.assertEquals(4, GetGcd.gcd3(8, 20));
		Assert.assertEquals(10, GetGcd.gcd3(10, 10)); 
	}
}
