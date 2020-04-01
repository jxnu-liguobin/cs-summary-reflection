package io.github.dreamylost.tooffer;

/**
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项。
 * n<=39
 */
public class T7 {

    public int Fibonacci(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return this.Fibonacci(n - 1) + this.Fibonacci(n - 2);
    }

    public int Fibonacci2(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int a = 1;
        int b = 1;
        int c = 0;
        for (int i = 3; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    public int Fibonacci3(int n, int acc1, int acc2) {
        if (n == 0) return 0;
        if (n == 1) return acc2;
        else return Fibonacci3(n - 1, acc2, acc1 + acc2);

    }

    public static void main(String[] args) {
        T7 t7 = new T7();
        for (int i = 1; i < 40; i++) {
            System.out.println(t7.Fibonacci2(i));
        }
    }
}
