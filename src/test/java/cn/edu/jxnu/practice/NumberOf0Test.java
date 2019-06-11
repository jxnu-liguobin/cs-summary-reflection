package cn.edu.jxnu.practice;

import org.junit.Assert;
import org.junit.Test;

public class NumberOf0Test {

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
