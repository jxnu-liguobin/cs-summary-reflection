package io.github.dreamylost.tooffer;

/** 操作给定的二叉树，将其变换为源二叉树的镜像。 */
public class T18 {

    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        Mirror(root.left);
        Mirror(root.right);
    }
}
