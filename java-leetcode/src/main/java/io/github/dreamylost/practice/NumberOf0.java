/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * 参考：编程之美
 *
 * @description 计算n的阶乘中，末尾含有0的个数
 * @author Mr.Li
 */
public class NumberOf0 {
    public static void main(String[] args) {
        int n = 1024;

        int result = NumberOf0_2(n);
        System.out.println(n + "的阶乘中含有：" + result + "个0");
    }

    public static int NumberOf0_1(int n) {
        int ret = 0;
        int j;
        for (int i = 1; i <= n; i++) {
            j = i;
            while (j % 5 == 0) {
                ret++;
                j /= 5;
            }
        }
        return ret;
    }

    /**
     * @description 使用公式Z=[N/5]+[N/5^2]+[N/5^3]....{存在K使得5^K>N即[N/5^K]==0} [N/5]=不大于N的数中，5的倍数贡献一个5
     *     [N/5^2]=不大于N的数中，5^2的倍数贡献一个5 上公式意味：[N/K]=1,2,3....N中能被k整除的数的个数
     */
    public static int NumberOf0_2(int n) {
        int ret = 0;
        while (n != 0) {
            ret += n / 5;
            n /= 5;
        }
        return ret;
    }
}
