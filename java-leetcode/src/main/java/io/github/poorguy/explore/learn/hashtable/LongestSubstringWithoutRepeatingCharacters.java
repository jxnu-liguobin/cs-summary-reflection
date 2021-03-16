/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.HashMap;
import java.util.Map;

class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        // Character as key and index as value
        Map<Character, Integer> map = new HashMap<>();
        // two pointers
        int l = 0;
        int max = 1;
        for (int r = 0; r < s.length(); r++) {
            if (map.containsKey(s.charAt(r))) {
                l = Math.max(l, map.get(s.charAt(r)));
                if (s.charAt(l) == s.charAt(r)) {
                    l++;
                }
            }
            max = Math.max(max, r - l + 1);
            map.put(s.charAt(r), r);
        }
        return max;
    }
}
