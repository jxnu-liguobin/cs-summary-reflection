/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;
/**
 * Title: PublicTreePrint.java
 *
 * <p>Description:
 *
 * <p>Copyright: Copyright (c) 2018
 *
 * <p>School: jxnu
 *
 * @author Mr.Li
 * @date 2018-2-7
 * @version 1.0
 */
public class PublicTreePrint {

    /**
     * @description 测试用例使用
     * @param node
     */
    /* 将二叉树先序遍历，用于测试结果 */
    public static void preTraverseBinTree(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + ",");
        if (node.left != null) {
            preTraverseBinTree(node.left);
        }
        if (node.right != null) {
            preTraverseBinTree(node.right);
        }
    }

    /* 将二叉树中序遍历，用于测试结果 */
    public static void inTraverseBinTree(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            inTraverseBinTree(node.left);
        }
        System.out.print(node.val + ",");
        if (node.right != null) {
            inTraverseBinTree(node.right);
        }
    }

    /* 将二叉树后序遍历，用于测试结果 */
    public static void postTraverseBinTree(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            postTraverseBinTree(node.left);
        }
        if (node.right != null) {
            postTraverseBinTree(node.right);
        }
        System.out.print(node.val + ",");
    }
}
