/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Using bfs or dfs will be better */
class NumberOfIslands {
    public int numIslands(char[][] grid) {
        List<Set<String>> resultList = new ArrayList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int line = 0; line < grid[0].length; line++) {
                union(resultList, grid, row, line);
            }
        }
        return resultList.size();
    }

    private void union(List<Set<String>> result, char[][] grid, int row, int line) {
        if (grid[row][line] == '0') {
            return;
        }

        List<Integer> unionedIndex = new ArrayList<>();
        // up
        if (row > 0) {
            for (int i = 0; i < result.size(); i++) {
                Set<String> set = result.get(i);
                if (set.contains(row - 1 + "," + line)) {
                    set.add(row + "," + line);
                    unionedIndex.add(i);
                }
            }
        }
        // left
        if (line > 0) {
            for (int i = 0; i < result.size(); i++) {
                Set<String> set = result.get(i);
                if (set.contains(row + "," + (line - 1))) {
                    set.add(row + "," + line);
                    unionedIndex.add(i);
                }
            }
        }

        if (unionedIndex.isEmpty()) {
            Set<String> set = new HashSet<>();
            set.add(row + "," + line);
            result.add(set);
        } else if (unionedIndex.size() == 2) {
            if (unionedIndex.get(0).equals(unionedIndex.get(1))) {
                result.get(unionedIndex.get(0)).add(row + "," + line);
            } else {
                if (row == 2 && line == 1) {
                    for (Integer i : unionedIndex) {
                        System.out.println(i);
                    }
                }
                result.get(unionedIndex.get(0)).addAll(result.get(unionedIndex.get(1)));
            }
        } else {
            result.get(unionedIndex.get(0)).add(row + "," + line);
        }
    }
}
