/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.HashMap;
import java.util.Map;

// Using a custom map with array will be faster
class IsomorphicStrings {
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        int counter = 0;
        for (int i = 0; i < chars1.length; i++) {
            Integer n1 = map1.get(chars1[i]);
            Integer n2 = map2.get(chars2[i]);
            if (n1 == null && n2 == null) {
                map1.put(chars1[i], counter);
                map2.put(chars2[i], counter);
                counter++;
            } else if (n1 != null && !n1.equals(n2)) {
                return false;
            } else if (n2 != null && !n2.equals(n1)) {
                return false;
            }
        }
        return true;
    }
}
