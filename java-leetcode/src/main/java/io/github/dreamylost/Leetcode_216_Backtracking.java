/* All Contributors (C) 2020 */
package io.github.dreamylost;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 从 1-9 数字中选出 k 个数，使得它们的和为 n。
 *
 * <p>Input: k = 3, n = 9
 *
 * <p>1~9的数字,k[3]位数组合和为n[9] Output:
 *
 * <p>[[1,2,6], [1,3,5], [2,3,4]]
 *
 * @author 梦境迷离.
 * @version v1.0
 * @time 2018年4月21日
 */
public class Leetcode_216_Backtracking {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String str = s.nextLine();
        int m = Integer.parseInt(str.split(",")[0]);
        int n = Integer.parseInt(str.split(",")[1]);
        List<List<Integer>> ret = new Leetcode_216_Backtracking().combinationSum3(m, n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        ret.forEach(
                x -> {
                    String result =
                            x.stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(",", "[", "],"));
                    stringBuilder.append(result);
                });
        stringBuilder.append("]");
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
        System.out.println(stringBuilder.toString());
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ret = new ArrayList<>();
        List<List<Integer>> ret2 = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            path.add(i);
            backtracking(k - 1, n - i, path, i, ret);
            path.remove(0);
        }
        // [1~n/2,1~n/2]
        int temp = n / 2;
        while (temp > 0) {
            List<Integer> path2 = new ArrayList<>();
            path2.add(temp);
            path2.add(temp);
            ret.add(path2);
            temp--;
        }

        // [m,n],[n,m]
        for (List<Integer> list : ret) {
            int n1 = list.get(0);
            int n2 = list.get(1);
            if (n1 == n2) {
                continue;
            }
            List<Integer> path2 = new ArrayList<>();
            path2.add(n2);
            path2.add(n1);
            ret2.add(path2);
        }
        ret.addAll(ret2);
        ret.sort(
                (o1, o2) -> {
                    if (o1.get(0) > o2.get(0)) {
                        return 1;
                    }
                    if (o1.get(0).equals(o2.get(0))) {
                        if (o1.get(1) > o2.get(1)) {
                            return 1;
                        }
                        if (o1.get(1).equals(o2.get(1))) {
                            return 0;
                        }
                        return -1;
                    }
                    return -1;
                });
        return ret;
    }

    /**
     * k个数，和为n的回溯
     *
     * @param k
     * @param n
     * @param path 临时结果集
     * @param start 开启标记位
     * @param ret 结果集
     * @author 梦境迷离.
     * @time 2018年4月21日
     * @version v1.0
     */
    private void backtracking(
            int k, int n, List<Integer> path, int start, List<List<Integer>> ret) {
        if (k == 0 && n >= 0) { // ==
            ret.add(new ArrayList<>(path));
            return;
        }
        if (k == 0 || n == 0) return;
        // 只能访问下一个元素，防止遍历的结果重复
        for (int i = start + 1; i <= 9; i++) {
            path.add(i);
            backtracking(k - 1, n - i, path, i, ret);
            path.remove(path.size() - 1);
        }
    }
}
