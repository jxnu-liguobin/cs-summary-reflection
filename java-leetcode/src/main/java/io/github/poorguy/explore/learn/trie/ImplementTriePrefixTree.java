/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.trie;

import java.util.HashMap;
import java.util.Map;

class ImplementTriePrefixTree {
    private Character val;
    private boolean isEnd;
    private Map<Character, ImplementTriePrefixTree> children;

    /** Initialize your data structure here. */
    public ImplementTriePrefixTree() {
        val = null;
        isEnd = false;
        children = null;
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null || "".equals(word)) {
            return;
        }
        char[] chars = word.toCharArray();
        ImplementTriePrefixTree pointer = this;
        for (char c : chars) {
            if (pointer.children == null) {
                pointer.children = new HashMap<>();
            }
            if (pointer.children.containsKey(c)) {
                pointer = pointer.children.get(c);
            } else {
                ImplementTriePrefixTree trie = new ImplementTriePrefixTree();
                trie.val = c;
                pointer.children.put(c, trie);
                pointer = trie;
            }
        }
        pointer.isEnd = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        if (word == null || "".equals(word)) {
            return false;
        }
        char[] chars = word.toCharArray();
        ImplementTriePrefixTree pointer = this;
        for (char c : chars) {
            if (pointer.children == null || pointer.children.isEmpty()) {
                return false;
            }
            ImplementTriePrefixTree child = pointer.children.get(c);
            if (child == null) {
                return false;
            } else {
                pointer = child;
            }
        }
        return pointer.isEnd;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        if (prefix == null || "".equals(prefix)) {
            return false;
        }
        char[] chars = prefix.toCharArray();
        ImplementTriePrefixTree pointer = this;
        for (char c : chars) {
            if (pointer.children == null || pointer.children.isEmpty()) {
                return false;
            }
            ImplementTriePrefixTree child = pointer.children.get(c);
            if (child == null) {
                return false;
            } else {
                pointer = child;
            }
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such: Trie obj = new Trie();
 * obj.insert(word); boolean param_2 = obj.search(word); boolean param_3 = obj.startsWith(prefix);
 */
