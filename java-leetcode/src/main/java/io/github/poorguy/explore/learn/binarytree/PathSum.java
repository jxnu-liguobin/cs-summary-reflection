/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class PathSum {
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == sum;
        }
        return hasPathSum(root, 0, sum);
    }

    private boolean hasPathSum(TreeNode node, int alreadySum, int sum) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null) {
            return alreadySum + node.val == sum;
        }
        return hasPathSum(node.left, alreadySum + node.val, sum)
                || hasPathSum(node.right, alreadySum + node.val, sum);
    }
}
