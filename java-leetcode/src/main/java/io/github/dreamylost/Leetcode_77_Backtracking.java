/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;
/**
 * 1~n，K位数的组合[1到4之间的数字组成的两位数的组合] If n = 4 and k = 2, a solution is: [ [2,4], [3,4], [2,3], [1,2],
 * [1,3], [1,4], ]
 *
 * @author 梦境迷离
 * @time. 2018年4月20日
 * @version v1.0
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 梦境迷离.
 * @time 2018年4月20日
 * @version v1.0
 */
public class Leetcode_77_Backtracking {

    public static void main(String[] args) {
        List<List<Integer>> ret = new Leetcode_77_Backtracking().combine(4, 2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");
        ret.forEach(
                x -> {
                    String result =
                            x.stream()
                                    .map(j -> j.toString())
                                    .collect(Collectors.joining(",", "[", "]"));
                    stringBuilder.append(" " + result);
                    stringBuilder.append("\n");
                });
        stringBuilder.append("]");
        System.out.println(stringBuilder.toString());
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> combineList = new ArrayList<>();
        backTracking(1, n, k, combineList, ret);
        return ret;
    }

    /**
     * 减枝函数，类似回溯。
     *
     * @author 梦境迷离.
     * @time 2018年4月20日
     * @version v1.0
     * @param start
     * @param n
     * @param k
     * @param combineList
     * @param ret
     */
    private void backTracking(
            int start, int n, int k, List<Integer> combineList, List<List<Integer>> ret) {
        if (k == 0) {
            ret.add(new ArrayList<>(combineList));
            return;
        }
        for (int i = start; i <= n - k + 1; i++) { // 剪枝
            combineList.add(i); // 标记i为已访问
            backTracking(i + 1, n, k - 1, combineList, ret); // 标记i为未访问
            combineList.remove(combineList.size() - 1);
        }
    }
}
