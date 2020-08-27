/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目: 电话号码的字母组合
 *
 * <p>给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * <p>给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * <p>思路: 回溯直接AC
 *
 * @author kongwiki@163.com
 * @since 2020/8/26下午2:49
 */
public class LetterCombinationsOfAPhoneNumber {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }
        Map<Character, String> map = new HashMap<>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
        String cur = "";
        backtrack(digits, res, cur, map, 0);
        return res;
    }

    private void backtrack(
            String digits, List<String> res, String cur, Map<Character, String> map, int start) {
        if (cur.length() == digits.length()) {
            res.add(cur);
            return;
        }

        for (int i = start; i < digits.length(); i++) {
            for (char ch : map.get(digits.charAt(i)).toCharArray()) {
                backtrack(digits, res, cur + ch, map, i + 1);
            }
        }
    }
}
