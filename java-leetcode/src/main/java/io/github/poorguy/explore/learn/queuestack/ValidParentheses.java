/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.ArrayDeque;
import java.util.Deque;

class ValidParentheses {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char ch = (char) (stack.pop());
                if (ch == '{') {
                    if (c != '}') {
                        return false;
                    } else {
                        continue;
                    }
                } else if (ch == '(') {
                    if (c != ')') {
                        return false;
                    } else {
                        continue;
                    }
                } else {
                    if (c != ']') {
                        return false;
                    } else {
                        continue;
                    }
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }
}
