package cn.edu.jxnu.leetcode;

import java.util.Collections;
import java.util.List;

/**
 * @author 梦境迷离
 * @description 最长子序列
 * <p>
 * Input: s = "abpcplea", d = ["ale","apple","monkey","plea"]
 * <p>
 * Output: "apple"
 * <p>
 * 题目描述：删除 s 中的一些字符，使得它构成字符串列表 d
 * 中的一个字符串，找出能构成的最长字符串。如果有多个相同长度的结果，返回按字典序排序的最大字符串。
 * @time 2018年4月7日
 */
public class Leetcode_524 {
    public String findLongestWord(String s, List<String> d) {
        String ret = "";
        for (String str : d) {
            for (int i = 0, j = 0; i < s.length() && j < str.length(); i++) {
                if (s.charAt(i) == str.charAt(j))
                    j++;
                if (j == str.length()) {
                    // 取长度最大的，且字典排序最大的
                    if (ret.length() < str.length() || (ret.length() == str.length() && ret.compareTo(str) > 0)) {
                        ret = str;
                    }
                }
            }
        }
        return ret;
    }

    public String findLongestWord2(String s, List<String> d) {
        if (s.length() == 0 || d.size() == 0) return "";

        Collections.sort(d, (a, b) -> {
            if (a.length() != b.length()) return b.length() - a.length();
            return a.compareTo(b);
        });

        for (String p : d) {
            if (s.length() < p.length()) continue;
            if (isSubSeq(s, p)) return p;
        }

        return "";
    }

    private boolean isSubSeq(String s, String p) {
        int i = 0, j = 0;
        while (i < s.length() && j < p.length()) {
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            } else {
                i++;
            }
        }
        return j == p.length();
    }
}
