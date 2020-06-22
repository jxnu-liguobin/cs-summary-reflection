/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 二进制加法
 *
 * <p>67. Add Binary (Easy)
 *
 * <p>a = "11" b = "1" Return "100"
 *
 * @author 梦境迷离.
 * @time 2018年6月23日
 * @version v1.0
 */
public class Leetcode_67_Math {

    public String addBinary(String a, String b) {
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        StringBuilder str = new StringBuilder();
        while (carry == 1 || i >= 0 || j >= 0) {
            if (i >= 0 && a.charAt(i--) == '1') {
                carry++;
            }
            if (j >= 0 && b.charAt(j--) == '1') {
                carry++;
            }
            str.append(carry % 2);
            carry /= 2;
        }
        return str.reverse().toString();
    }
}
