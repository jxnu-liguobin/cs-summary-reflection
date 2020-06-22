/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.other;

import java.util.Scanner;

/**
 * @author 梦境迷离
 * @time 2018-09-28
 */
public class TestMain5 {

    /**
     * 跳跃
     *
     * @param arr
     * @return
     */
    public static int jump(int[] arr) {

        if (arr == null || arr.length == 0) {
            return 0;
        }
        int jump = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < arr.length; i++) {
            if (cur < i) {
                jump++;
                cur = next;
            }
            next = Math.max(next, i + arr[i]);
        }
        return jump;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine() + ",";
        String[] nums = input.split(",");
        int[] ns = new int[nums.length];
        int k = 0;
        for (String s : nums) {
            ns[k++] = Integer.parseInt(s);
        }
        int ret = jump(ns);
        System.out.println(ret);
    }
}
