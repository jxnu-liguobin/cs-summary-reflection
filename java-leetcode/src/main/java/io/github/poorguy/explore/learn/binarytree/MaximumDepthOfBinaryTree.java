/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class MaximumDepthOfBinaryTree {
    public int maxDepth(TreeNode root) {
        return findDepth(root, 1);
    }

    private int findDepth(TreeNode node, int depth) {
        if (node == null) {
            return depth - 1;
        }
        if (node.left == null && node.right == null) {
            return depth;
        }
        return Math.max(findDepth(node.left, depth + 1), findDepth(node.right, depth + 1));
    }
}
