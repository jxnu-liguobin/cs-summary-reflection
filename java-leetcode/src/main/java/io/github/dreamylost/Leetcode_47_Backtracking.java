package io.github.dreamylost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Leetcode : 47. Permutations II (Medium) [1,1,2] have the following unique permutations: [[1,1,2],
 * [1,2,1], [2,1,1]] 题目描述：数组元素可能含有相同的元素，进行排列时就有可能出现 重复的排列，要求重复的排列只返回一个。 在实现上，和
 * Permutations不同的是要先排序，然后在添加一个元素时，判断这个元素是否等于前一个元素，如果等于， 并且前一个元素还未访问，那么就跳过这个元素。
 *
 * @author 梦境迷离
 * @version V1.0
 * @time. 2018年4月13日
 */
public class Leetcode_47_Backtracking {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2};
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        list = new Leetcode_47_Backtracking().permuteUnique(nums);
        list.forEach(
                x -> {
                    String s =
                            x.stream()
                                    .map(i -> i.toString())
                                    .collect(Collectors.joining(",", "[", "]"));
                    System.out.println(s);
                });
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> permuteList = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] visited = new boolean[nums.length];
        backtracking(permuteList, visited, nums, ret);
        return ret;
    }

    /**
     * 对abc操作 固定a, 求后面bc的全排列 固定c, 求后面bc的全排列 固定c, 求后面ba的全排列
     *
     * @time. 下午6:49:09
     * @version V1.0
     * @param permuteList
     * @param visited
     * @param nums
     * @param ret
     */
    private void backtracking(
            List<Integer> permuteList, boolean[] visited, int[] nums, List<List<Integer>> ret) {
        if (permuteList.size() == nums.length) {
            ret.add(new ArrayList<Integer>(permuteList)); // 重新构造一个 List
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            /**
             * 考虑以下示例：i=0，1，2，3，4，a=1、1、1、2、2在for循环中，在i=0中，我们将形成形式[1]+置换({1，1，2，2})的排列。
             * 在i=1中，！[0]意味着我们正在进行下一个迭代，我们不需要再考虑[1]+置换({1，1，2，2})。 在i=2，！[1]意味着我们已经考虑过类似的排列。
             * 但是，并不是使用[0]不可以是true，而且对于{1，1，2，2}的排列也可能发生。
             */
            if (i != 0 && nums[i] == nums[i - 1] && !visited[i - 1]) continue; // 防止重复
            if (visited[i]) continue;
            visited[i] = true;
            permuteList.add(nums[i]);
            backtracking(permuteList, visited, nums, ret);
            permuteList.remove(permuteList.size() - 1);
            visited[i] = false;
        }
    }
}
