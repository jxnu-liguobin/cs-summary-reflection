/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * LCP 1. 猜数字 此种题 不支持rust Scala
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2020-03-13
 */
public class Leetcode_LCP_1 {

    public static void main(String[] args) {
        int count = game(new int[] {1, 2, 3}, new int[] {1, 2, 3});
        System.out.println(count);
    }

    public static int game(int[] guess, int[] answer) {
        int flag = 0;
        for (int j = 0; j < answer.length; j++) {
            if (guess[j] == answer[j]) {
                flag++;
            }
        }
        return flag;
    }
}
