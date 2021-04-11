/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class DecodeString {
    public String decodeString(String s) {
        List<Character> resultList = new ArrayList<>();
        Deque<List<Character>> strStack = new ArrayDeque<>();
        Deque<Integer> numStack = new ArrayDeque<>();
        List<Character> cache = new ArrayList<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (isChar(c)) {
                cache.add(c);
            } else if (isNumber(c)) {
                List<Character> tmp = new ArrayList<>();
                tmp.add(c);
                while (i + 1 < chars.length && isNumber(chars[i + 1])) {
                    tmp.add(chars[++i]);
                }
                numStack.push(Integer.parseInt(charListToString(tmp)));
            } else if (c == '[') {
                strStack.push(new ArrayList<>(cache));
                cache.clear();
            } else if (c == ']') {
                int num = numStack.pop();
                List<Character> strList = strStack.pop();
                if (strList == null) {
                    strList = new ArrayList<>();
                }
                List<Character> newList = new ArrayList<>(strList.size() + cache.size() * num);
                newList.addAll(strList);
                for (int j = 0; j < num; j++) {
                    newList.addAll(cache);
                }
                cache = newList;
            }
        }
        return charListToString(cache);
    }

    private String charListToString(List<Character> list) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : list) {
            sb.append(ch);
        }
        return sb.toString();
    }

    private boolean isChar(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    public static void main(String[] args) {
        DecodeString decodeString = new DecodeString();
        String s = "100[leetcode]";
        String s1 = decodeString.decodeString(s);
        System.out.println("result: " + s1);
    }
}
