/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class UniqueBinarySearchTrees2 {
    public List<TreeNode> generateTrees(int n) {
        return helper(1, n);
    }

    private List<TreeNode> helper(int mid, int n) {
        List<TreeNode> result = new ArrayList<>();
        if (mid > n) {
            result.add(null);
            return result;
        }
        for (int i = mid; i <= n; i++) {
            List<TreeNode> leftList = helper(mid, i - 1);
            List<TreeNode> rightList = helper(i + 1, n);
            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }
        return result;
    }
}
