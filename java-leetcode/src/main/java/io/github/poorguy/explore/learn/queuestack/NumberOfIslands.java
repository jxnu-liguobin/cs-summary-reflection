/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import io.github.poorguy.tag.DFS;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class NumberOfIslands implements DFS {
    public int numIslands(char[][] grid) {
        return dfs(grid);
    }

    private int dfs(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                count += dfs(grid, i, j);
            }
        }
        return count;
    }

    private int dfs(char[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            return 0;
        }
        if (grid[row][col] == '1') {
            grid[row][col] = '0';
            dfs(grid, row - 1, col);
            dfs(grid, row + 1, col);
            dfs(grid, row, col + 1);
            dfs(grid, row, col - 1);
            return 1;
        } else {
            return 0;
        }
    }

    private int union(char[][] grid) {
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
