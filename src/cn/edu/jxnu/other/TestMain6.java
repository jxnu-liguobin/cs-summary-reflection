package cn.edu.jxnu.other;

import java.util.Scanner;

/**
 * 只有一个数字出现了2次，求这个数
 *
 * @author 梦境迷离
 * @time 2018-09-28
 */
public class TestMain6 {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine() + ",";
        String[] nums = input.split(",");
        int[] ns = new int[101];
        int k = 0;
        for (String s : nums) {
            ns[k++] = Integer.parseInt(s);
        }
        int ret = find(ns);
        System.out.println(ret);
    }

    public static int find(int a[]) {
        int temp = 0;
        for (int i = 0; i < 101; i++) {
            temp = temp ^ a[i];
        }
        return temp ^ 100;
    }
}
