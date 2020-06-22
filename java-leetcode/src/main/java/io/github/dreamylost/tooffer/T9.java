/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

/** 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。 */
public class T9 {

    public static void main(String[] args) {
        T9 t9 = new T9();
        System.out.println(t9.JumpFloorII(3));
    }

    /**
     * 链接：https://www.nowcoder.com/questionTerminal/22243d016f6b47f2a6928b4313c85387 来源：牛客网
     *
     * <p>因为n级台阶，第一步有n种跳法：跳1级、跳2级、到跳n级 跳1级，剩下n-1级，则剩下跳法是f(n-1) 跳2级，剩下n-2级，则剩下跳法是f(n-2)
     * 所以f(n)=f(n-1)+f(n-2)+...+f(1) 因为f(n-1)=f(n-2)+f(n-3)+...+f(1) 所以f(n)=2*f(n-1)
     */
    public int JumpFloorII(int target) {
        if (target == 1 || target == 2) {
            return target;
        } else {
            return 2 * this.JumpFloorII(target - 1);
        }
    }

    public int JumpFloorII2(int target) {
        return 1 << (target - 1);
    }
}
