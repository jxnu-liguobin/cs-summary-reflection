package io.github.dreamylost.practice;

/**
 * Title: StringDemo2.java
 *
 * <p>Description:判断字符串s2能否被s1循环移位得到
 *
 * <p>Copyright: Copyright (c) 2018
 *
 * <p>School: jxnu
 *
 * @author Mr.Li
 * @date 2018-2-9
 * @version 1.0
 */
public class StringDisplacement {

    /** @param args */
    public static void main(String[] args) {
        String s1 = "AABBCD";
        String s2 = "CDAA";
        Boolean b1 = StringDisplacement.stringDisplacement(s1, s2);
        Boolean b2 = StringDisplacement.stringDisplacement2(s1, s2);
        System.out.println(b1);
        System.out.println(b2);
    }

    /**
     * @description 字符串s2能否被s1循环移位得到->字符串s2必被包含于s1s1
     * @param s1
     * @param s2
     * @return
     */
    public static Boolean stringDisplacement2(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder(s1);
        sb.append(s1);
        if (sb.toString().contains(s2)) {
            System.out.println("************************************");
            System.out.println("src:" + s1);
            System.out.println("src*2:" + sb.toString());
            System.out.println("des:" + s2);
            System.out.println("************************************");
            return true;
        }
        return false;
    }

    /**
     * @description 字符串较大时，效率很低
     * @param s1
     * @param s2
     * @return
     */
    public static boolean stringDisplacement(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        char src[] = s1.toCharArray();
        int len = src.length;
        for (int i = 0; i < len; i++) {

            // 保存第一个元素，循环移位
            char tempchar = src[0];
            for (int j = 0; j < len - 1; j++) {
                src[j] = src[j + 1];
            }
            src[len - 1] = tempchar;
            // 判断是否包含
            if (String.valueOf(src).contains(s2)) {
                System.out.println("src:" + String.valueOf(src));
                System.out.println("des:" + s2);
                return true;
            }
        }
        return false;
    }
}
