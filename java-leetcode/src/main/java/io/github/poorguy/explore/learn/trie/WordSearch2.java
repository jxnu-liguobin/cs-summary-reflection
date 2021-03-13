/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** todo a little slow */
class WordSearch2 {
    private class Node {
        Character ch = null;
        Node[] children = new Node[26];
        boolean isEnd = false;
    }

    private Set<String> result = new HashSet<>();

    public List<String> findWords(char[][] board, String[] words) {
        Node head = new Node();
        Node pointer = head;
        for (String word : words) {
            for (Character ch : word.toCharArray()) {
                if (pointer.children[ch - 'a'] == null) {
                    Node child = new Node();
                    child.ch = ch;
                    pointer.children[ch - 'a'] = child;
                }
                pointer = pointer.children[ch - 'a'];
            }
            pointer.isEnd = true;
            pointer = head;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                pointer = head;
                search(
                        pointer,
                        i,
                        j,
                        board,
                        new boolean[board.length][board[0].length],
                        new ArrayList<>());
            }
        }

        return new ArrayList<>(result);
    }

    private boolean search(
            Node pointer,
            int row,
            int col,
            char[][] board,
            boolean[][] flag,
            List<Character> chars) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || flag[row][col]) {
            return false;
        }

        char ch = board[row][col];
        Node child = pointer.children[ch - 'a'];
        if (child == null) {
            return false;
        }
        flag[row][col] = true;

        chars.add(child.ch);
        if (child.isEnd) {
            result.add(listToString(chars));
        }

        boolean hasNext = false;
        if (search(child, row - 1, col, board, flag, chars)) {
            hasNext = true;
        }
        if (search(child, row + 1, col, board, flag, chars)) {
            hasNext = true;
        }
        if (search(child, row, col - 1, board, flag, chars)) {
            hasNext = true;
        }
        if (search(child, row, col + 1, board, flag, chars)) {
            hasNext = true;
        }
        chars.remove(chars.size() - 1);
        flag[row][col] = false;
        return child.isEnd || hasNext;
    }

    private String listToString(List<Character> list) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : list) {
            sb.append(sb);
        }
        return sb.toString();
    }

    // [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]]
    // ["oath","pea","eat","rain"]
    public static void main(String[] args) {
        WordSearch2 solution = new WordSearch2();
        char[][] board = {
            {'o', 'a', 'a', 'n'}, {'e', 't', 'a', 'e'}, {'i', 'h', 'k', 'r'}, {'i', 'f', 'l', 'v'}
        };
        String[] words = {"oath", "pea", "eat", "rain"};
        List<String> wordList = solution.findWords(board, words);
        wordList.forEach(System.out::println);
    }
}
