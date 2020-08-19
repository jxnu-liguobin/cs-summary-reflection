/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import io.github.wkk.structs.TreeNode;

/**
 * @author kongwiki@163.com
 * @since 2020/8/17上午9:10
 */
public class BalancedBinaryTree {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return Math.abs(leftHeight - rightHeight) <= 1
                && isBalanced(root.left)
                && isBalanced(root.right);
    }

    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left);
        int right = height(node.right);
        return Math.max(left, right) + 1;
    }
}
