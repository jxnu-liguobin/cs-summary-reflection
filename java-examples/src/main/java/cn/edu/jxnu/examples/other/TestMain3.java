package cn.edu.jxnu.examples.other;

import java.util.Scanner;

public class TestMain3 {
    /*
    任意多个数字组合, 和为定值

    5
    1 2 3 4 5
    10

    4
    3 1 5 9
    14
    */

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        N = input.nextInt();
        nums = new int[N];
        for (int i = 0; i < N; i++) nums[i] = input.nextInt();
        target = input.nextInt();

        dfs(0, 0);
        System.out.println(flag);

        input.close();
    }

    private static int target = 0;
    private static int[] nums;
    private static int N;
    private static boolean flag = false;

    public static void dfs(int depth, int sum) {
        if (sum == target) {
            flag = true;
            return;
        }
        //        if (flag == true)    // 可以更快收敛
        //            return ;
        for (int i = depth; i < N; i++) {
            dfs(depth + 1, sum + nums[depth]);
            dfs(depth + 1, sum);
        }
    }
}
