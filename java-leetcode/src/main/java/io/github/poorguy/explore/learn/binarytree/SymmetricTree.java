/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left != null && right == null) {
            return false;
        }
        if (right != null && left == null) {
            return false;
        }
        if (right.val == left.val) {
            boolean sideResult = isSymmetric(left.left, right.right);
            boolean innerResult = isSymmetric(left.right, right.left);
            return sideResult && innerResult;
        }
        return false;
    }
}
