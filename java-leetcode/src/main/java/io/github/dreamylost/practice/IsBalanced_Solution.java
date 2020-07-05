/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 输入一棵二叉树，判断该二叉树是否是平衡二叉树。
 * @author Mr.Li
 */
public class IsBalanced_Solution {

    /**
     * @description 求二叉树树深度
     * @param root
     * @return
     */
    private int getTreeDepth(TreeNode root) {
        if (root == null) return 0;
        int left_height = getTreeDepth(root.left);
        int right_height = getTreeDepth(root.right);
        return 1 + (left_height > right_height ? left_height : right_height);
    }

    public boolean isBalanced_Solution(TreeNode root) {
        if (root == null) return true;
        int left_height = getTreeDepth(root.left);
        int right_height = getTreeDepth(root.right);
        if (Math.abs(left_height - right_height) > 1) return false;
        else return isBalanced_Solution(root.left) && isBalanced_Solution(root.right);
    }
}
