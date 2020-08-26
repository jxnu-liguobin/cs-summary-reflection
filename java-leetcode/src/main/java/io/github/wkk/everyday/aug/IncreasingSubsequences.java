/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kongwiki@163.com
 * @since 2020/8/25上午10:46
 */
public class IncreasingSubsequences {
    /** 回溯: 超出时间限制 */
    public static List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> out = new ArrayList<>();
        backtrack(res, out, nums, 0);
        return res;
    }

    private static void backtrack(
            List<List<Integer>> res, List<Integer> out, int[] nums, int start) {
        if (out.size() > 1 && !res.contains(out)) {
            res.add(new ArrayList<>(out));
        }
        if (start >= nums.length) {
            return;
        }

        for (int i = start; i < nums.length; i++) {
            if (!out.isEmpty() && out.get(out.size() - 1) > nums[i]) {
                continue;
            }
            out.add(nums[i]);
            backtrack(res, out, nums, i + 1);
            out.remove(out.size() - 1);
        }
    }

    /** 递归枚举+剪枝 */
    List<Integer> temp = new ArrayList<Integer>();

    List<List<Integer>> ans = new ArrayList<List<Integer>>();

    public List<List<Integer>> findSubsequencesII(int[] nums) {
        dfs(0, Integer.MIN_VALUE, nums);
        return ans;
    }

    public void dfs(int cur, int last, int[] nums) {
        if (cur == nums.length) {
            if (temp.size() >= 2) {
                ans.add(new ArrayList<Integer>(temp));
            }
            return;
        }
        if (nums[cur] >= last) {
            temp.add(nums[cur]);
            dfs(cur + 1, nums[cur], nums);
            temp.remove(temp.size() - 1);
        }
        if (nums[cur] != last) {
            dfs(cur + 1, last, nums);
        }
    }

    public static void main(String[] args) {
        int[] numbers = {4, 6, 7, 7};
        List<List<Integer>> subsequences = findSubsequences(numbers);
        System.out.println(subsequences);
    }
}
