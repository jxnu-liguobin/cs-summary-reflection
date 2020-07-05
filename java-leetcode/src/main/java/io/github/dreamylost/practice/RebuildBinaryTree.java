/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.Arrays;

/**
 * @description 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列
 *     {1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}， 则重建二叉树并返回
 * @author Mr.Li
 */
public class RebuildBinaryTree {
    /**
     * @description 递归 传递引用
     * @param pre
     * @param in
     * @return
     */
    public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        // 非空判断
        if (pre == null || in == null) {
            return null;
        }
        TreeNode mm = reConstructBinaryTreeCore(pre, in, 0, pre.length - 1, 0, in.length - 1);
        return mm;
    }

    /**
     * @description 封装函数
     * @param pre
     * @param in
     * @param preStart
     * @param preEnd
     * @param inStart
     * @param inEnd
     * @return
     */
    private static TreeNode reConstructBinaryTreeCore(
            int[] pre, int[] in, int preStart, int preEnd, int inStart, int inEnd) {
        /* 获取前序遍历的第一个节点 即根节点 */
        TreeNode tree = new TreeNode(pre[preStart]);
        tree.left = null;
        tree.right = null;
        /* 只有一个节点 */
        if (preStart == preEnd && inStart == inEnd) {
            return tree;
        }
        int r = 0;
        /* 寻找左子树的位置 */
        for (r = inStart; r < inEnd; r++) {
            if (pre[preStart] == in[r]) {
                break;
            }
        }
        /* 得到左右子树的节点个数 */
        int leifLength = r - inStart;
        int rightLength = inEnd - r;
        /* 继续递归构建 */
        if (leifLength > 0) {
            tree.left =
                    reConstructBinaryTreeCore(
                            pre, in, preStart + 1, preStart + leifLength, inStart, r - 1);
        }
        if (rightLength > 0) {
            tree.right =
                    reConstructBinaryTreeCore(
                            pre, in, preStart + 1 + leifLength, preEnd, r + 1, inEnd);
        }
        return tree;
    }

    /**
     * @description 递归 拷贝数组 参考牛客网
     * @param pre
     * @param in
     * @return
     */
    public TreeNode reConstructBinaryTree2(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length || pre.length == 0) return null;
        int root = pre[0];
        int rootIndexOfInorder;
        for (rootIndexOfInorder = 0; rootIndexOfInorder < in.length; rootIndexOfInorder++) {
            if (in[rootIndexOfInorder] == root) break;
        }
        TreeNode node = new TreeNode(root);
        TreeNode left =
                reConstructBinaryTree(
                        Arrays.copyOfRange(pre, 1, rootIndexOfInorder + 1),
                        Arrays.copyOfRange(in, 0, rootIndexOfInorder));
        TreeNode right =
                reConstructBinaryTree(
                        Arrays.copyOfRange(pre, rootIndexOfInorder + 1, pre.length),
                        Arrays.copyOfRange(in, rootIndexOfInorder + 1, in.length));
        node.left = left;
        node.right = right;
        return node;
    }
}
