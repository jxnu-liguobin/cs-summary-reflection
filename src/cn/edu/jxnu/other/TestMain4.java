package cn.edu.jxnu.other;

import java.util.Scanner;

/**
 * @author 梦境迷离
 * @time 2018-09-16
 */
public class TestMain4 {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int count = 0;
        for (int i = 1; i <= n; i++) {
            boolean ret = f(i);
            if (ret) {
                count++;
            }

        }
        System.out.println(count);

    }

    public static boolean f(int n) {

        String s = String.valueOf(n);
        char[] arr = s.toCharArray();
        int len = arr.length;
        int k = 0;
        boolean fg = false;
        for (char c : arr) {
            if (c == '0' && fg) {
                return false;
            }
            if (c == '3' || c == '7' || c == '4') {
                return false;
            } else if (c == '1' || c == '8') {
                fg = true;
                k++;

            }
        }
        if (k == len) {
            return false;
        }
        return true;
    }
}
