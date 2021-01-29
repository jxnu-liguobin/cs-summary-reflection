/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class BinaryTreeLevelOrderTraversal {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        ArrayDeque<List<TreeNode>> stack = new ArrayDeque<>();
        if (root == null) {
            return new ArrayList<>();
        }
        stack.push(Arrays.asList(root));
        while (!stack.isEmpty()) {
            List<TreeNode> currList = stack.pop();
            List<TreeNode> nextList = new ArrayList<>();
            if (!currList.isEmpty()) {
                List<Integer> levelResult = new ArrayList<>();
                for (TreeNode node : currList) {
                    levelResult.add(node.val);
                    if (node.left != null) {
                        nextList.add(node.left);
                    }
                    if (node.right != null) {
                        nextList.add(node.right);
                    }
                }
                result.add(levelResult);
                stack.push(nextList);
            } else {
                return result;
            }
        }
        return result;
    }
}
