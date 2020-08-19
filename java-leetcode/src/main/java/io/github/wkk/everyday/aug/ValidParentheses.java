/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kongwiki@163.com
 * @since 2020/8/14上午10:44
 */
public class ValidParentheses {
    /**
     * 括号相关问题思路: 使用栈
     *
     * <p>相关题目: 括号的生成, 最长有效括号
     */
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            // 右半边
            if (map.containsKey(c)) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (!stack.peek().equals(map.get(c))) {
                    // 无法匹配到对应的左半边
                    return false;
                } else {
                    stack.pop();
                }

            } else {
                // 放入左半边括号
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {}
}
