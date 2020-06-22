/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.HashSet;

/**
 * 给定一个数将其转换为二进制（均用字符串表示），如果这个数的小数部分不能在 32 个字符之内来精确地表示，则返回 "ERROR"。
 *
 * @author 梦境迷离
 * @time 2018年8月10日
 * @version v1.0
 */
public class BinaryRepresentation {

    /**
     * 注意不能用Double.parseDouble, 会出现精度错误。正确做法是把其分两段，整数位和小数位都用long 来表示。 小数的二进制转换是将其乘以2,
     * 再取100000的模(假定初始小数部分为5位数）
     */
    private String parseInteger(String str) {
        int n = Integer.parseInt(str);
        if (str.equals("") || str.equals("0")) {
            return "0";
        }
        String binary = "";
        while (n != 0) {
            binary = Integer.toString(n % 2) + binary;
            n = n / 2;
        }
        return binary;
    }

    private String parseFloat(String str) {
        double d = Double.parseDouble("0." + str);
        String binary = "";
        HashSet<Double> set = new HashSet<Double>();
        while (d > 0) {
            if (binary.length() > 32 || set.contains(d)) {
                return "ERROR";
            }
            set.add(d);
            d = d * 2;
            if (d >= 1) {
                binary = binary + "1";
                d = d - 1;
            } else {
                binary = binary + "0";
            }
        }
        return binary;
    }

    public String binaryRepresentation(String n) {
        if (n.indexOf('.') == -1) {
            return parseInteger(n);
        }
        String[] params = n.split("\\.");
        String flt = parseFloat(params[1]);
        if (flt == "ERROR") {
            return flt;
        }
        if (flt.equals("0") || flt.equals("")) {
            return parseInteger(params[0]);
        }
        return parseInteger(params[0]) + "." + flt;
    }
}
