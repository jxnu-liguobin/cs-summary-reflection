/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.IsThePowerOf2;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class IsThePowerOf2_test {

    @Test
    public void testPowerOf2() {
        Assert.assertEquals(-1, IsThePowerOf2.powerOf2(5L));
        Assert.assertEquals(0, IsThePowerOf2.powerOf2(1L));
        Assert.assertEquals(1, IsThePowerOf2.powerOf2(2L));
    }
}
