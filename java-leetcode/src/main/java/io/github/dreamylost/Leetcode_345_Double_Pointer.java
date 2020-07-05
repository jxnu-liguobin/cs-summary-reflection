/* All Contributors (C) 2020 */
package io.github.dreamylost;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author 梦境迷离
 * @description 反转字符串中的元音字符 使用双指针，指向待反转的两个元音字符，一个指针从头向尾遍历，一个指针从尾到头遍历。
 * @time 2018年4月7日
 */
public class Leetcode_345_Double_Pointer {
    private HashSet<Character> vowels =
            new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

    public String reverseVowels(String s) {
        if (s.length() == 0) return s;
        int i = 0, j = s.length() - 1;
        // 额外空间，存储临时字符
        char[] result = new char[s.length()];
        while (i <= j) {
            char ci = s.charAt(i);
            char cj = s.charAt(j);
            // ci不是元音
            if (!vowels.contains(ci)) {
                result[i] = ci;
                i++;
            } else if (!vowels.contains(cj)) {
                // cj不是元音
                result[j] = cj;
                j--;
            } else {
                // 反转i j
                result[i] = cj;
                result[j] = ci;
                // 继续推进
                i++;
                j--;
            }
        }
        return new String(result);
    }
}
