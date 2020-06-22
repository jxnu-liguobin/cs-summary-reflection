/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice.test;

import io.github.dreamylost.practice.GetNumberOfK;
import org.junit.Assert;
import org.junit.Test;

/**
 * 迁移代码，原提交者Perkins
 *
 * @author Perkins
 * @version v1.0
 * @time 2019-06-19
 */
public class GetNumberOfK_test {

    @Test
    public void testGetNumberOfK() {
        int[] array = {4, 6, 1, 6, 1, 7, 6};

        Assert.assertEquals(0, new GetNumberOfK().getNumberOfK(new int[0], 0));
        Assert.assertEquals(1, new GetNumberOfK().getNumberOfK(array, 4));
        Assert.assertEquals(3, new GetNumberOfK().getNumberOfK(array, 6));
    }

    @Test
    public void testGetNumberOfK2() {
        int[] array = {1, 2, 6, 1, 4, 5, 5, 5, 6, 8};

        Assert.assertEquals(0, new GetNumberOfK().GetNumberOfK2(new int[0], 0));
        Assert.assertEquals(0, new GetNumberOfK().GetNumberOfK2(array, 3));
        Assert.assertEquals(3, new GetNumberOfK().GetNumberOfK2(array, 5));
    }

    @Test
    public void testGetFirst() {
        int[] array = {-2, -1, 0, 1, 2, 3, 4, 5, 6, 7};

        Assert.assertEquals(-1, GetNumberOfK.getFirst(array, 11, 0, 0));
        Assert.assertEquals(-1, GetNumberOfK.getFirst(array, 0, -1, 0));
        Assert.assertEquals(-1, GetNumberOfK.getFirst(array, 6, 9, 2));
        Assert.assertEquals(7, GetNumberOfK.getFirst(array, 0, 19, 5));
    }

    @Test
    public void testGetLast() {
        int[] array = {5, 5, 7, 1, 2, 5, 1, 8, 5, 5};

        Assert.assertEquals(-1, GetNumberOfK.getLast(array, 11, 0, 0));
        Assert.assertEquals(-1, GetNumberOfK.getLast(array, 0, -1, 0));
        Assert.assertEquals(-1, GetNumberOfK.getLast(array, 0, 4, 2));
        Assert.assertEquals(9, GetNumberOfK.getLast(array, 8, 9, 5));
    }
}
