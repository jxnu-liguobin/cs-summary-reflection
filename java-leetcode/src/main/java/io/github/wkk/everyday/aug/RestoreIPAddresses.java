/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import java.util.ArrayList;
import java.util.List;

/**
 * 复原IP地址(IP格式: 每一位都在0-255之间)
 *
 * <p>思路: 1.暴力求解 2.回溯(涉及到求所有的结果, 常用方法)
 *
 * @author kongwiki@163.com
 * @since 2020/8/9上午9:33
 */
public class RestoreIPAddresses {
    /** 暴力求解 需要一个辅助方法, 判断每个子串是否符合IP格式 1. 每位在0-255之间 2. 不能出现0开头的两位以上数字 */
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0 || s.length() > 12) {
            return res;
        }
        int n = s.length();
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < i + 4; j++) {
                for (int k = j + 1; k < j + 4; k++) {
                    if (j < n && k < n) {
                        String a = s.substring(0, i + 1);
                        String b = s.substring(i + 1, j + 1);
                        String c = s.substring(j + 1, k + 1);
                        String d = s.substring(k + 1);
                        if (helper(a) && helper(b) && helper(c) && helper(d)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(a)
                                    .append(".")
                                    .append(b)
                                    .append(".")
                                    .append(c)
                                    .append(".")
                                    .append(d);
                            res.add(sb.toString());
                        }
                    }
                }
            }
        }
        return res;
    }

    private boolean helper(String s) {
        if (s == null
                || s.length() == 0
                || s.length() > 3
                || s.charAt(0) == '0' && s.length() > 1
                || Integer.parseInt(s) > 255) {
            return false;
        }
        return true;
    }

    /** 回溯 */
    public List<String> restoreIpAddressesII(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0 || s.length() > 12) {
            return res;
        }
        int n = s.length();
        backtrack(0, "", 4, s, n, res);
        return res;
    }

    /** 回溯本体 */
    private void backtrack(int start, String tmp, int flag, String s, int n, List<String> res) {
        if (start == n && flag == 0) {
            res.add(tmp.substring(0, tmp.length() - 1));
            return;
        }
        if (flag < 0) {
            return;
        }
        for (int j = start; j < start + 3; j++) {
            if (j < n) {
                if (start == j && s.charAt(j) == '0') {
                    backtrack(j + 1, tmp + s.charAt(j) + ".", flag - 1, s, n, res);
                    break;
                }
                if (Integer.parseInt(s.substring(start, j + 1)) <= 255) {
                    backtrack(j + 1, tmp + s.substring(start, j + 1) + ".", flag - 1, s, n, res);
                }
            }
        }
    }
}
