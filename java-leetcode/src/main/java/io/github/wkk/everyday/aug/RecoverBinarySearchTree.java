/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import io.github.wkk.structs.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 恢复二叉搜索树
 *
 * <p>难点: 找出真正出问题的两个点
 *
 * <p>思路: 根据其性质, 使用中序遍历, 标记出真正出现问题的两个位置, 之后交换其元素值即可
 *
 * <p>抽象: .....errorOne |increasing sequence | errorTwo .......
 *
 * <p>第一个节点,是第一个按照中序遍历时候前一个节点大于后一个节点,我们选取前一个节点
 *
 * <p>第二个节点,是在第一个节点找到之后, 后面出现前一个节点大于后一个节点,我们选择后一个节点
 *
 * @author kongwiki@163.com
 * @since 2020/8/8上午8:59
 */
public class RecoverBinarySearchTree {
    private TreeNode errorOne, errorTwo;

    public void recoverTree(TreeNode root) {
        helper(root);
        swap(errorOne, errorTwo);
    }

    private void helper(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode pre = null;
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode p = root;
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                if (pre != null && pre.val > p.val) {
                    if (errorOne == null) {
                        errorOne = pre;
                        errorTwo = p;
                    } else {
                        errorTwo = p;
                    }
                }
                pre = p;
                p = p.right;
            }
        }
    }

    private void swap(TreeNode first, TreeNode second) {
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }
}
