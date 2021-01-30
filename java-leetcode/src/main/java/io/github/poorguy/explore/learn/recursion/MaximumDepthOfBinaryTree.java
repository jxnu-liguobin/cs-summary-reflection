/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class MaximumDepthOfBinaryTree {
    public int maxDepth(TreeNode root) {
        return maxDepth(root, 0);
    }

    private int maxDepth(TreeNode root, int alreadyDepth) {
        if (root == null) {
            return alreadyDepth;
        }
        if (root.left == null && root.right == null) {
            return 1 + alreadyDepth;
        }
        if (root.left != null && root.right == null) {
            return maxDepth(root.left, 1 + alreadyDepth);
        }
        if (root.left == null && root.right != null) {
            return maxDepth(root.right, 1 + alreadyDepth);
        }
        if (root.left != null && root.right != null) {
            return Math.max(
                    maxDepth(root.left, 1 + alreadyDepth), maxDepth(root.right, 1 + alreadyDepth));
        }
        return -1;
    }
}
