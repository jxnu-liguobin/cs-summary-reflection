/* All Contributors (C) 2020 */
package io.github.ccccmaster;

/**
 * 拼写单词 26个桶 记录chars对应数字 为每个单词建桶 记录char对应数量 某个单词所有字符数 <= chars中数量 即可组成
 *
 * @author chenyu
 * @date 2020-07-05.
 */
public class Leetcode_1160 {

    public int countCharacters(String[] words, String chars) {
        int[] index = new int[26];
        char[] cs = chars.toCharArray();
        for (char c : cs) {
            index[c - 'a'] = index[c - 'a'] + 1;
        }
        int allLen = 0;
        for (String word : words) {
            if (isInIndex(word, index)) {
                allLen += word.length();
            }
        }
        return allLen;
    }

    private boolean isInIndex(String words, int[] index) {
        int[] in = new int[26];
        char[] cs = words.toCharArray();
        for (char c : cs) {
            in[c - 'a'] = in[c - 'a'] + 1;
        }
        for (int i = 0; i < index.length; i++) {
            if (in[i] > index[i]) {
                return false;
            }
        }
        return true;
    }
}
