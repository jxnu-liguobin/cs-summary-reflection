/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode(int x) { val = x; } }
 */
class LowestCommonAncestorOfABinaryTree {
    private static TreeNode result = null;

    private boolean recurseTree(TreeNode currentNode, TreeNode p, TreeNode q) {
        if (currentNode == null) {
            return false;
        }
        int left = recurseTree(currentNode.left, p, q) ? 1 : 0;
        int right = recurseTree(currentNode.right, p, q) ? 1 : 0;
        int mid = (currentNode.val == p.val || currentNode.val == q.val) ? 1 : 0;
        if (mid + left + right == 2) {
            result = currentNode;
        }
        return mid + right + left > 0;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        recurseTree(root, p, q);
        return result;
    }
}
