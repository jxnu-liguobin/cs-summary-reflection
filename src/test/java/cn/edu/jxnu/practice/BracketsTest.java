package cn.edu.jxnu.practice;

import org.junit.Assert;
import org.junit.Test;

public class BracketsTest {

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
