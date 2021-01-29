/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class BinaryTreeInorderTraversal {
    public List<Integer> inorderTraversal(TreeNode root) {
        return iterate(root);
    }

    public List<Integer> iterate(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.add(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            result.add(curr.val);
            curr = curr.right;
        }
        return result;
    }

    public void recursive(List<Integer> result, TreeNode root) {
        if (root != null) {
            recursive(result, root.left);
            result.add(root.val);
            recursive(result, root.right);
        }
    }
}
