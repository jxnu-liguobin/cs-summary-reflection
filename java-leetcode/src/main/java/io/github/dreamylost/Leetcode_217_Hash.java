/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.HashSet;
import java.util.Set;

/**
 * 判断数组是否含有重复元素
 *
 * <p>217. Contains Duplicate (Easy)
 *
 * @author 梦境迷离.
 * @time 2018年7月2日
 * @version v1.0
 */
public class Leetcode_217_Hash {

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        return set.size() < nums.length;
    }
}
