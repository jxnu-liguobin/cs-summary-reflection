package io.github.dreamylost;

import java.util.HashMap;

/**
 * 数组中的两个数和为给定值
 *
 * <p>1. Two Sum (Easy)
 *
 * <p>可以先对数组进行排序，然后使用双指针方法或者二分查找方法。这样做的时间复杂度为 O(NlogN)，空间复杂度为 O(1)。
 *
 * <p>用 HashMap 存储数组元素和索引的映射，在访问到 nums[i] 时，判断 HashMap 中是否存在 target - nums[i]，如果存在说明 target -
 * nums[i] 所在的索引和 i 就是要找的两个数。该方法的时间复杂度为 O(N)，空间复杂度为 O(N)，使用空间来换取时间。
 *
 * @author 梦境迷离.
 * @time 2018年7月2日
 * @version v1.0
 */
public class Leetcode_1_Hash {

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> indexForNum = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (indexForNum.containsKey(target - nums[i])) {
                return new int[] {indexForNum.get(target - nums[i]), i};
            } else {
                indexForNum.put(nums[i], i);
            }
        }
        return null;
    }
    //	Scala
    //	def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    //	        import scala.collection.mutable
    //	        val m = mutable.Map[Int, Int]()
    //	        val ans = Array[Int](0, 0)
    //	        var i = 0
    //	        while (i < nums.length) {
    //	            if (m.contains(target - nums(i))){
    //	                ans(0) = m(target - nums(i))
    //	                ans(1) = i
    //	                return ans
    //	            }
    //	            else {
    //	                m += (nums(i) -> i)
    //	            }
    //	            i += 1
    //	        }
    //
    //	        return ans
    //	    }

}
