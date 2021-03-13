/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ReplaceWords {
    private class Trie {
        private Character ch = null;
        private Map<Character, Trie> children = new HashMap<>();
        private boolean isEnd = false;

        private void insert(String str) {
            Trie pointer = this;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (pointer.children.containsKey(c)) {
                    pointer = pointer.children.get(c);
                } else {
                    Trie child = new Trie();
                    child.ch = c;
                    pointer.children.put(c, child);
                    pointer = child;
                }
            }
            pointer.isEnd = true;
        }

        // if no prefix, return str. else return shortest prefix
        private String prefix(String str) {
            Trie pointer = this;
            char[] chars = str.toCharArray();
            List<Character> result = new ArrayList<>();
            boolean end = false;
            for (char c : chars) {
                if (!pointer.children.containsKey(c)) {
                    break;
                } else {
                    result.add(c);
                    Trie child = pointer.children.get(c);
                    if (child.isEnd) {
                        end = true;
                        break;
                    }
                    pointer = child;
                }
            }
            if (!end) {
                return str;
            }
            return result.stream().map(Object::toString).collect(Collectors.joining(""));
        }
    }

    public String replaceWords(List<String> dictionary, String sentence) {
        Trie trie = new Trie();
        for (String str : dictionary) {
            trie.insert(str);
        }
        String[] words = sentence.split(" ");
        List<String> wordList = new ArrayList<>(words.length);
        for (String word : words) {
            wordList.add(trie.prefix(word));
        }
        return String.join(" ", wordList);
    }
}
