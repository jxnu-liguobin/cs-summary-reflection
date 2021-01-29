/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    /** pStart start index of sub-preorder array pEnd end index of sub-preorder array */
    private TreeNode buildTree(
            int[] preorder, int[] inorder, int pStart, int pEnd, int inStart, int inEnd) {
        if (pStart > pEnd || inStart > inEnd) {
            return null;
        }
        int rootVal = preorder[pStart];
        TreeNode root = new TreeNode(rootVal);

        int inorderSplitPoint = getIndex(inorder, inStart, rootVal);
        TreeNode left =
                buildTree(
                        preorder,
                        inorder,
                        pStart + 1,
                        pStart + inorderSplitPoint - inStart,
                        inStart,
                        inorderSplitPoint - 1);
        TreeNode right =
                buildTree(
                        preorder,
                        inorder,
                        pStart + inorderSplitPoint - inStart + 1,
                        pEnd,
                        inorderSplitPoint + 1,
                        inEnd);
        root.left = left;
        root.right = right;
        return root;
    }

    // get index of element in array, the index always existes
    private int getIndex(int[] array, int startIndex, int val) {
        for (int i = startIndex; ; i++) {
            if (array[i] == val) {
                return i;
            }
        }
    }
}
