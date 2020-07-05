/* All Contributors (C) 2020 */
package io.github.dreamylost.tooffer;

import java.util.HashMap;

/** 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符, 并返回它的位置, 如果没有则返回 -1（需要区分大小写）. */
public class T34 {

    public int FirstNotRepeatingChar(String str) {
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            if (hashMap.containsKey(str.charAt(i))) {
                str = str.replace(str.charAt(i), ' ');
            } else {
                hashMap.put(str.charAt(i), 0);
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                return i;
            }
        }
        return -1;
    }
}
