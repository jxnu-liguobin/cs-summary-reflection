/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.*;

// 可以用质数代替每个字符，用他们的乘积代表字符串中所有元素
class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<Integer>> map = new HashMap<>();
        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < strs.length; i++) {
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);
            String sort = String.valueOf(chars);
            List<Integer> indexList = map.get(sort);
            if (indexList == null) {
                List<Integer> newIndexList = new ArrayList<>();
                newIndexList.add(i);
                map.put(sort, newIndexList);
            } else {
                indexList.add(i);
                map.put(sort, indexList);
            }
        }

        for (List<Integer> indexList : map.values()) {
            List<String> strList = new ArrayList<>(indexList.size());
            for (Integer index : indexList) {
                strList.add(strs[index]);
            }
            result.add(strList);
        }
        return result;
    }
}
