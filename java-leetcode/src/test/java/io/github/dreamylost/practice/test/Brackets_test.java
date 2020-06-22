/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.Brackets;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
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
