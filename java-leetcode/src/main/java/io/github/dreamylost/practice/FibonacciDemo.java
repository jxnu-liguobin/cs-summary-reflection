/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项。n<=39
 * @author Mr.Li
 */
public class FibonacciDemo {

    public static void main(String[] args) throws Exception {
        System.out.println(new FibonacciDemo().Fibonacci4(39));
    }

    public int Fibonacci(int n) throws Exception {

        if (n > 39) {
            throw new Exception(n + " is too big to this function that maybe make OOM!");
        }
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return Fibonacci(n - 1) + Fibonacci(n - 2);
        }
    }

    public int Fibonacci2(int n) throws Exception {
        if (n < 2) {
            return n;
        }
        if (n > 39) {
            throw new Exception(n + " is too big to this function that maybe make OOM!");
        }
        int target = 0;
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i++) {
            target = first + second;
            first = second;
            second = target;
        }

        return target;
    }

    /**
     * @descripion 动态规划版 来自牛客
     * @param n
     * @return
     */
    public int Fibonacci3(int n) {
        int f = 0, g = 1;
        while (n-- > 0) {
            g += f;
            f = g - f;
        }
        return f;
    }

    /**
     * @description 动态规划版 来自牛客 牺牲空间
     * @param n
     * @return
     */
    public int Fibonacci4(int n) {
        if (n <= 1) {
            return n;
        }
        int[] record = new int[n + 1];
        record[0] = 0;
        record[1] = 1;
        for (int i = 2; i <= n; i++) {
            record[i] = record[i - 1] + record[i - 2];
        }
        return record[n];
    }
}
