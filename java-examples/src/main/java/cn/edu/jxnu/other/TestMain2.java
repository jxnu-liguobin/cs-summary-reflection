/* All Contributors (C) 2020 */
package cn.edu.jxnu.other;

import java.util.Scanner;

/**
 * @author 梦境迷离
 * @time 2018-09-11
 */
public class TestMain2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        boolean ret = util(n);
        System.out.println(ret);
    }

    // 求素数
    private static boolean util(int n) {
        boolean ret = true;
        int m = (int) Math.sqrt(n);
        for (int i = 2; i < m; i++) {
            if (n % i == 0) {
                ret = false;
                break;
            }
        }
        return ret;
    }
}
