/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.GetGcd;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class GetGcd_test {

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
