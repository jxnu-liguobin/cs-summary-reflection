/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kongwiki@163.com
 * @since 2020/8/6上午9:23
 */
public class PalindromePairs {

    /** n: 数组长度 k: 每个单词的平均长度 方法1: 直接暴力解决, 时间复杂度O(n^2*k), 超时 */
    public static List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        int n = words.length;
        for (int i = 1; i < n; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (isPalindromic(words[i] + words[j])) {
                    res.add(Arrays.asList(i, j));
                }
                ;
                if (isPalindromic(words[j] + words[i])) {
                    res.add(Arrays.asList(j, i));
                }
                j--;
            }
        }
        return res;
    }

    /** n: 数组长度 k: 每个单词的平均长度 方法2: 前缀树 时间复杂度O(n*k^2) */
    private Node root;

    public List<List<Integer>> palindromePairsII(String[] words) {
        this.root = new Node();
        int n = words.length;
        // 字典树的插入，注意维护每个节点上的两个列表
        for (int i = 0; i < n; i++) {
            String rev = new StringBuilder(words[i]).reverse().toString();
            Node cur = root;
            if (isPalindromic(rev.substring(0))) {
                cur.suffixs.add(i);
            }
            for (int j = 0; j < rev.length(); j++) {
                char ch = rev.charAt(j);
                if (cur.children[ch - 'a'] == null) {
                    cur.children[ch - 'a'] = new Node();
                }
                cur = cur.children[ch - 'a'];
                if (isPalindromic(rev.substring(j + 1))) {
                    cur.suffixs.add(i);
                }
            }
            cur.words.add(i);
        }
        // 用以存放答案的列表
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String word = words[i];
            Node cur = root;
            int j = 0;
            for (; j < word.length(); j++) {
                // 到j位置，后续字符串若是回文对，则在该节点位置上所有单词都可以与words[i]构成回文对
                if (isPalindromic(word.substring(j))) {
                    for (int k : cur.words) {
                        if (k != i) {
                            ans.add(Arrays.asList(i, k));
                        }
                    }
                }

                char ch = word.charAt(j);
                if (cur.children[ch - 'a'] == null) {
                    break;
                }
                cur = cur.children[ch - 'a'];
            }
            // words[i]遍历完了，现在找所有大于words[i]长度且符合要求的单词，suffixs列表就派上用场了:)
            if (j == word.length()) {
                for (int k : cur.suffixs) {
                    if (k != i) {
                        ans.add(Arrays.asList(i, k));
                    }
                }
            }
        }
        return ans;
    }

    private class Node {
        public Node[] children;
        public List<Integer> words;
        public List<Integer> suffixs;

        public Node() {
            this.children = new Node[26];
            this.words = new ArrayList<>();
            this.suffixs = new ArrayList<>();
        }
    }

    /**
     * 判断是否是回文串
     *
     * @param a String
     * @return boolean
     */
    private static boolean isPalindromic(String a) {
        // 双指针
        int low = 0;
        int high = a.length() - 1;
        while (low < high) {
            if (a.charAt(low) != a.charAt(high)) {
                return false;
            } else {
                low++;
                high--;
            }
        }
        return true;
    }
}
