/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.Find;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class Find_test {

    @Test
    public void testFind() {
        Assert.assertFalse(Find.find(new int[][] {{-1, 1}}, 0));

        Assert.assertTrue(Find.find(new int[][] {{-5}}, -5));
    }

    @Test
    public void testFind2() {
        Assert.assertFalse(new Find().Find2(new int[][] {}, 1));
        Assert.assertFalse(new Find().Find2(new int[][] {{4}}, 0));
        Assert.assertFalse(new Find().Find2(new int[][] {}, 4));
        Assert.assertFalse(new Find().Find2(new int[][] {{1}}, 4));

        Assert.assertTrue(new Find().Find2(new int[][] {{-1, 1, 2}}, 2));
    }
}
