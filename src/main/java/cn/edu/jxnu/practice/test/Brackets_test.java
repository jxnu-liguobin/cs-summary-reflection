package cn.edu.jxnu.practice.test;

import cn.edu.jxnu.practice.Brackets;
import org.junit.Assert;
import org.junit.Test;

public class Brackets_test {

	@Test
	public void testSolve() {
		Assert.assertEquals("NO", Brackets.solve("3"));
		Assert.assertEquals("NO", Brackets.solve("("));
		Assert.assertEquals("NO", Brackets.solve(")"));
		Assert.assertEquals("NO", Brackets.solve("(3)"));
		Assert.assertEquals("Yes", Brackets.solve("((3))"));
		Assert.assertEquals("Yes", Brackets.solve("(((3)))"));
	}
}
