/* All Contributors (C) 2020 */
package io.github.poorguy.test;

import io.github.poorguy.BinarySearch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Binary Search Test
 *
 * @author 王一凡
 * @date 7/22/20
 */
public class BinarySearchTest {
    @Test
    public void search() {
        int[] nums_1 = new int[] {-1, 0, 3, 5, 9, 12};
        int target_1 = 9;
        Assert.assertEquals(4, BinarySearch.search(nums_1, target_1));
        int target_2 = 2;
        Assert.assertEquals(-1, BinarySearch.search(nums_1, target_2));
    }
}
