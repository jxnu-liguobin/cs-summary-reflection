/* All Contributors (C) 2020 */
package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.NumberOf0;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class NumberOf0_test {

    @Test
    public void testNumberOf0_1() {
        Assert.assertEquals(0, NumberOf0.NumberOf0_1(0));
        Assert.assertEquals(0, NumberOf0.NumberOf0_1(1));
        Assert.assertEquals(1, NumberOf0.NumberOf0_1(5));
        Assert.assertEquals(8, NumberOf0.NumberOf0_1(35));
    }

    @Test
    public void testNumberOf0_2() {
        Assert.assertEquals(0, NumberOf0.NumberOf0_2(0));
        Assert.assertEquals(0, NumberOf0.NumberOf0_2(1));
        Assert.assertEquals(1, NumberOf0.NumberOf0_2(5));
        Assert.assertEquals(10, NumberOf0.NumberOf0_2(45));
    }
}
