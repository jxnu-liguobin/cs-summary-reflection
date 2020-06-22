/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

public class LocationOf1FromRight {

    /**
     * @description 二进制中最低位1 的位置【离右最近的1】
     * @param args
     */
    public static void main(String[] args) {
        int n = 2;
        int result = lowestLocationOf1(n);
        System.out.println(n + "的阶乘中最低位1的位置是：" + result);
    }

    /**
     * 使用公式Z=[N/2]+[N/4]+[N/8]....{存在K使得2^K>N即[N/2^K]==0} 上公式意味：[N/K]=1,2,3....N中能被k整除的数的个数
     *
     * @description 等价于求N！中含有质因数2的个数+1
     */
    public static int lowestLocationOf1(int n) {
        int ret = 0;
        while (n != 0) {
            n >>= 1;
            ret += n;
        }
        return ret + 1;
    }
}
