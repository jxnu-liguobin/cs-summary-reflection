package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.DifferentNumberOfBinaryBits_Java;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class DifferentNumberOfBinaryBits_Java_test {

    @Test
    public void testBitSwapRequired() {
        Assert.assertEquals(0, DifferentNumberOfBinaryBits_Java.bitSwapRequired(0, 0));
        Assert.assertEquals(4, DifferentNumberOfBinaryBits_Java.bitSwapRequired(250, 147));
        Assert.assertEquals(5, DifferentNumberOfBinaryBits_Java.bitSwapRequired(-250, -147));
    }
}
