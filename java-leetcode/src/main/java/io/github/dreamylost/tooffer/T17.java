/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

/** 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构） */
public class T17 {

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return false;
        }
        if (root1 == null && root2 != null) {
            return false;
        }
        if (root1 != null && root2 == null) {
            return false;
        }
        boolean result = false;
        if (root1.val == root2.val) {
            result = isSubTree(root1, root2);
        }
        if (!result) {
            result = HasSubtree(root1.left, root2);
        }
        if (!result) {
            result = HasSubtree(root1.right, root2);
        }
        return result;
    }

    public static boolean isSubTree(TreeNode node1, TreeNode node2) {
        if (node2 == null) {
            return true;
        }
        if (node1 == null) {
            return false;
        }
        if (node1.val != node2.val) {
            return false;
        }
        return isSubTree(node1.left, node2.left) && isSubTree(node1.right, node2.right);
    }
}
