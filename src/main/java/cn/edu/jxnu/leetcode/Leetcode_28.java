package cn.edu.jxnu.leetcode;

/**
 * 实现 strStr() 函数。
 * <p>
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 *
 * @author 梦境迷离
 * @time 2018-09-20
 */
public class Leetcode_28 {

    /**
     * 取两个字符串的长度，l1,l2
     * 判断前者不小于后者，且后者不等于0，
     * 取长度的差，循环遍历，
     * 在haystack中取l2长度的字符，判断是否等于needle
     * 有则返回
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr3(String haystack, String needle) {
        int l1 = haystack.length(), l2 = needle.length();
        if (l1 < l2) {
            return -1;
        } else if (l2 == 0) {
            return 0;
        }
        int threshold = l1 - l2;
        for (int i = 0; i <= threshold; i++) {
            if (haystack.substring(i, i + l2).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    public int strStr(String haystack, String needle) {
        for (int i = 0; ; i++) {
            for (int j = 0; ; j++) {
                if (j == needle.length())
                    return i;
                if (i + j == haystack.length())
                    return -1;
                if (needle.charAt(j) != haystack.charAt(i + j))
                    break;
            }
        }
    }

    /**
     * KMP
     *
     * @param haystack
     * @param needle
     * @return
     */
    public String strStr2(String haystack, String needle) {
        if (needle.equals(""))
            return haystack;
        if (haystack.equals(""))
            return null;
        char[] arr = needle.toCharArray();
        int[] next = makeNext(arr);
        for (int i = 0, j = 0, end = haystack.length(); i < end; ) {
            if (j == -1 || haystack.charAt(i) == arr[j]) {
                j++;
                i++;
                if (j == arr.length)
                    return haystack.substring(i - arr.length);
            }
            if (i < end && haystack.charAt(i) != arr[j])
                j = next[j];
        }
        return null;
    }

    private int[] makeNext(char[] arr) {
        int len = arr.length;
        int[] next = new int[len];
        next[0] = -1;
        for (int i = 0, j = -1; i + 1 < len; ) {
            if (j == -1 || arr[i] == arr[j]) {
                next[i + 1] = j + 1;
                if (arr[i + 1] == arr[j + 1])
                    next[i + 1] = next[j + 1];
                i++;
                j++;
            }
            if (arr[i] != arr[j])
                j = next[j];
        }

        return next;
    }
}
