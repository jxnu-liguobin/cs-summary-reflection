/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.HashMap;
import java.util.Map;

class FirstUniqueCharacterInAString {
    public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        boolean[] isDuplicated = new boolean[26];
        for (int i = 0; i < chars.length; i++) {
            if (isDuplicated[chars[i] - 'a']) {
                continue;
            }

            if (map.containsKey(chars[i])) {
                isDuplicated[chars[i] - 'a'] = true;
                map.remove(chars[i]);
            } else {
                map.put(chars[i], i);
            }
        }
        Integer minIndex = null;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (minIndex == null || minIndex > entry.getValue()) {
                minIndex = entry.getValue();
            }
        }
        return minIndex == null ? -1 : minIndex;
    }
}
