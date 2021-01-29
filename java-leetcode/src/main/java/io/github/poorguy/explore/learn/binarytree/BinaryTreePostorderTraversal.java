/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class BinaryTreePostorderTraversal {
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                result.addFirst(curr.val);
                curr = curr.right;
            }
            curr = stack.pop();
            curr = curr.left;
        }
        return result;
    }

    public void iterate(List<Integer> result, TreeNode root) {}

    public void recursive(List<Integer> result, TreeNode root) {
        if (root != null) {
            recursive(result, root.left);
            recursive(result, root.right);
            result.add(root.val);
        }
    }
}
