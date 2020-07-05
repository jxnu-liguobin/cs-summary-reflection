/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.Scanner;

/**
 * @author 梦境迷离
 * @description 某公司年会上，组织人员安排了一个小游戏来调节气氛。游戏规则如下： N个人参与游戏，站成一排来抢工作人抛来的M个小玩偶。为了增加游戏的趣味和难度，规则规定，
 *     参与游戏的人抢到的礼物不能比左右两边的人多两个或以上，否则会受到一定的惩罚。游戏结束时拥有玩偶最多的人将获得一份大奖。
 *     假设大家都想赢得这份大奖，请问站在第K个位置的小招在赢得游戏时，最多能拥有几个玩偶？
 * @time 2018年3月29日
 */
public class Main16 {
    /**
     * @author CS1_
     * @description 等差数列推导
     * @time 2018年3月29日
     */
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        if (K == 0 || N == 0 || K > N) {
            System.out.println(0);
            return;
        }
        int a = K * (K - 1) / 2;
        int b = (N - K + 1) * (N - K) / 2;
        System.out.println((M + a + b) / N);
        sc.close();
    }
}
