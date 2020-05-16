package io.github.dreamylost;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分割字符串使得每个部分都是回文数
 *
 * <p>131. Palindrome Partitioning (Medium)
 *
 * <p>For example, given s = "aab", Return
 *
 * <p>[ ["aa","b"], ["a","a","b"] ]
 *
 * @author 梦境迷离.
 * @time 2018年5月1日
 * @version v1.0
 */
public class Leetcode_131_Backtracking {

    /** 测试. */
    public static void main(String[] args) {

        String s = "aab";
        List<List<String>> ret = new Leetcode_131_Backtracking().partition(s);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");
        ret.forEach(
                x -> {
                    String result = x.stream().collect(Collectors.joining(",", "[", "],"));
                    stringBuilder.append(" " + result);
                    stringBuilder.append("\n");
                });
        stringBuilder.append("]");
        System.out.println(stringBuilder.toString());
    }

    private List<List<String>> ret;

    public List<List<String>> partition(String s) {
        ret = new ArrayList<>();
        doPartion(new ArrayList<>(), s);
        return ret;
    }

    private void doPartion(List<String> list, String s) {
        if (s.length() == 0) {
            ret.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            if (isPalindrome(s, 0, i)) {
                list.add(s.substring(0, i + 1));
                doPartion(list, s.substring(i + 1));
                list.remove(list.size() - 1);
            }
        }
    }

    /**
     * 判断回文
     *
     * @author 梦境迷离.
     * @time 2018年5月1日
     * @version v1.0
     * @param s
     * @param begin
     * @param end
     * @return 布尔值
     */
    private boolean isPalindrome(String s, int begin, int end) {
        while (begin < end) {
            if (s.charAt(begin++) != s.charAt(end--)) return false;
        }
        return true;
    }
}
