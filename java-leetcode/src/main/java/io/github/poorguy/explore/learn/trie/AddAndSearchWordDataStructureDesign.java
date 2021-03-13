/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.trie;

import java.util.ArrayList;
import java.util.List;

class AddAndSearchWordDataStructureDesign {
    private Character ch = null;
    private AddAndSearchWordDataStructureDesign[] children =
            new AddAndSearchWordDataStructureDesign[26];
    private boolean isEnd = false;

    /** Initialize your data structure here. */
    public AddAndSearchWordDataStructureDesign() {}

    public void addWord(String word) {
        AddAndSearchWordDataStructureDesign pointer = this;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            if (pointer.children[c - 'a'] == null) {
                AddAndSearchWordDataStructureDesign child =
                        new AddAndSearchWordDataStructureDesign();
                child.ch = c;
                pointer.children[c - 'a'] = child;
            }
            pointer = pointer.children[c - 'a'];
        }
        pointer.isEnd = true;
    }

    public boolean search(String word) {
        AddAndSearchWordDataStructureDesign pointer = this;
        char[] chars = word.toCharArray();
        List<Character> charList = new ArrayList<>(chars.length);
        for (char c : chars) {
            charList.add(c);
        }
        return search(charList, pointer);
    }

    private boolean search(List<Character> charList, AddAndSearchWordDataStructureDesign pointer) {
        if (charList.size() == 0) {
            return false;
        }
        if (charList.get(0) == '.') {
            for (int i = 0; i < 26; i++) {
                if (pointer.children[i] == null) {
                    continue;
                }
                if (pointer.children[i].isEnd && charList.size() == 1) {
                    return true;
                }
                boolean result = search(charList, pointer.children[i]);
                if (result) {
                    return true;
                }
                charList.add(0, '.');
            }
            return false;
        } else {
            if (pointer.children[charList.get(0) - 'a'] == null) {
                return false;
            }
            if (pointer.children[charList.get(0) - 'a'].isEnd && charList.size() == 1) {
                return true;
            }
            Character removed = charList.remove(0);
            boolean result = search(charList, pointer.children[removed - 'a']);
            if (result) {
                return true;
            }
            charList.add(0, removed);
        }
        return false;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such: WordDictionary obj = new
 * WordDictionary(); obj.addWord(word); boolean param_2 = obj.search(word);
 */
