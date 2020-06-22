/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.RightShift;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class RightShift_test {

    @Test
    public void testRightShift() {
        int[] array = {};
        RightShift.rightShift(new int[0], 1, 0);

        Assert.assertArrayEquals(new int[0], array);

        array = new int[] {1, 2, 5, 4, 5, 6, 7, 1, 9};
        RightShift.rightShift(array, 8, 5);

        Assert.assertArrayEquals(new int[] {4, 5, 6, 7, 1, 1, 2, 5, 9}, array);
    }

    @Test
    public void testRightShift2() {
        int[] array = {};
        RightShift.rightShift2(new int[0], 1, 0);

        Assert.assertArrayEquals(new int[0], array);

        array = new int[] {1, 2, 5, 4, 5, 6, 7, 1, 9};
        RightShift.rightShift2(array, 8, 5);

        Assert.assertArrayEquals(new int[] {4, 5, 6, 7, 1, 1, 2, 5, 9}, array);
    }

    @Test
    public void reverseArray() {
        int[] array = {1, 2, 3, 4, 5};
        RightShift.reverseArray(array, 1, 4);

        Assert.assertArrayEquals(new int[] {1, 5, 4, 3, 2}, array);
    }

    @Test
    public void testRightShift3() {
        int[] array = {};
        RightShift.rightShift3(new int[0], 1, 0);

        Assert.assertArrayEquals(new int[0], array);

        array = new int[] {1, 2, 5, 4, 5, 6, 7, 1, 9};
        RightShift.rightShift3(array, 8, 5);

        Assert.assertArrayEquals(new int[] {4, 5, 6, 7, 1, 1, 2, 5, 9}, array);
    }
}
