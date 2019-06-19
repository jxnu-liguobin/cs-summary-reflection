package cn.edu.jxnu.practice.test;

import cn.edu.jxnu.practice.Find;
import org.junit.Assert;
import org.junit.Test;

public class Find_test {

	@Test
	public void testFind() {
		Assert.assertFalse(Find.find(new int[][]{{-1, 1}}, 0));

		Assert.assertTrue(Find.find(new int[][]{{-5}}, -5));
	}

	@Test
	public void testFind2() {
		Assert.assertFalse(new Find().Find2(new int[][]{}, 1));
		Assert.assertFalse(new Find().Find2(new int[][]{{4}}, 0));
		Assert.assertFalse(new Find().Find2(new int[][]{}, 4));
		Assert.assertFalse(new Find().Find2(new int[][]{{1}}, 4));

		Assert.assertTrue(new Find().Find2(new int[][]{{-1, 1, 2}}, 2));
	}
}
