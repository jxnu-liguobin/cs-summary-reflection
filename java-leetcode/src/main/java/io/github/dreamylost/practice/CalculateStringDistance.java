package io.github.dreamylost.practice;

/**
 * Title: StringDemo4.java
 *
 * <p>Description: 计算字符串的相似度 ： 距离加1的倒数
 *
 * <p>Copyright: Copyright (c) 2018
 *
 * <p>School: jxnu
 *
 * @author Mr.Li
 * @date 2018-2-15
 * @version 1.0
 */
public class CalculateStringDistance {

    /** @param args */
    public static void main(String[] args) {
        String string1 = "abcdefg";
        String string2 = "abcdef";
        int s =
                new CalculateStringDistance()
                        .calculateStringDistance(
                                string1, 0, string1.length() - 1, string2, 0, string2.length() - 1);
        System.out.println((double) 1 / (s + 1));
    }

    /** @desciption 有大量重复的计算 */
    public int calculateStringDistance(
            String strA, int pABegin, int pAEnd, String strB, int pBBegin, int pBEnd) {
        // 因为不管两个字符串变成相等之后的字符串是怎样的，所以合并
        // 删除修改增加
        if (pABegin > pAEnd) {
            if (pBBegin > pBEnd) {
                return 0;
            } else {
                return pBEnd - pBBegin + 1;
            }
        }
        if (pBBegin > pBEnd) {
            if (pABegin > pAEnd) {
                return 0;
            } else {
                return pAEnd - pABegin + 1;
            }
        }
        if (strA.charAt(pABegin) == strB.charAt(pBBegin)) {
            return calculateStringDistance(strA, pABegin + 1, pAEnd, strB, pBBegin + 1, pBEnd);
        } else {
            /* 一步操作之后，把把sA[1...LenA]和sB[2...LenB]变成相同字符串 */
            int t1 = calculateStringDistance(strA, pABegin, pAEnd, strB, pBBegin + 1, pBEnd);
            /* 一步操作之后，把sA[2...LenA]和sB[1...LenB]变成相同字符串 */
            int t2 = calculateStringDistance(strA, pABegin + 1, pAEnd, strB, pBBegin, pBEnd);
            /* 一步操作之后，把把sA[2...LenA]和sB[2...LenB]变成相同字符串 */
            int t3 = calculateStringDistance(strA, pABegin + 1, pAEnd, strB, pBBegin + 1, pBEnd);
            return Math.min(Math.min(t1, t2), t3) + 1;
        }
    }
}
