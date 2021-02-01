/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

import java.util.*;

class LetterCombinationsOfAPhoneNumber {
    private static final Map<Character, List<Character>> map;

    static {
        map = new HashMap<>();
        map.put('2', Arrays.asList('a', 'b', 'c'));
        map.put('3', Arrays.asList('d', 'e', 'f'));
        map.put('4', Arrays.asList('g', 'h', 'i'));
        map.put('5', Arrays.asList('j', 'k', 'l'));
        map.put('6', Arrays.asList('m', 'n', 'o'));
        map.put('7', Arrays.asList('p', 'q', 'r', 's'));
        map.put('8', Arrays.asList('t', 'u', 'v'));
        map.put('9', Arrays.asList('w', 'x', 'y', 'z'));
    }

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<>();
        }

        char[] digitArray = digits.toCharArray();
        List<Character> digitList = new LinkedList<>();
        for (char character : digitArray) {
            digitList.add(character);
        }
        List<String> result = new ArrayList<>();
        helper(digitList, new ArrayList<>(), result);
        return result;
    }

    private void helper(List<Character> digitArray, List<Character> charList, List<String> result) {
        if (0 == digitArray.size()) {
            StringBuilder sb = new StringBuilder();
            for (Character character : charList) {
                sb.append(character);
            }
            result.add(sb.toString());
            return;
        }

        Character removed = digitArray.remove(0);
        for (Character selectedChar : map.get(removed)) {
            charList.add(selectedChar);
            helper(digitArray, charList, result);
            charList.remove(charList.size() - 1);
        }
        digitArray.add(0, removed);
    }
}
