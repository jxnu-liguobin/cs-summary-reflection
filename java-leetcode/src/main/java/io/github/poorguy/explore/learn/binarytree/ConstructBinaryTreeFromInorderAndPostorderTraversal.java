/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        List<Integer> inorderList = Arrays.stream(inorder).boxed().collect(Collectors.toList());
        List<Integer> postorderList = Arrays.stream(postorder).boxed().collect(Collectors.toList());
        return buildTreeRecursively(inorderList, postorderList);
    }

    private TreeNode buildTreeRecursively(List<Integer> inorder, List<Integer> postorder) {
        return getRootAndSplit(inorder, postorder);
    }

    private TreeNode getRootAndSplit(List<Integer> inorder, List<Integer> postorder) {
        if (postorder.size() == 0) {
            return null;
        }
        Integer rootVal = postorder.get(postorder.size() - 1);
        TreeNode root = new TreeNode(rootVal);

        Integer index = inorder.indexOf(rootVal);
        List<Integer> inorderLeft = inorder.subList(0, index);
        List<Integer> inorderRight;
        if (index == inorder.size()) {
            inorderRight = new ArrayList();
        } else {
            inorderRight = inorder.subList(index + 1, inorder.size());
        }
        List<Integer> postorderLeft = postorder.subList(0, inorderLeft.size());
        List<Integer> postorderRight = postorder.subList(inorderLeft.size(), inorder.size() - 1);

        TreeNode left = getRootAndSplit(inorderLeft, postorderLeft);
        TreeNode right = getRootAndSplit(inorderRight, postorderRight);
        root.left = left;
        root.right = right;
        return root;
    }
}
