/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.Deque;
import java.util.LinkedList;
/**
 * 面试题 17.12. BiNode
 *
 * <p>二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。
 * 实现一个方法，把二叉搜索树转换为单向链表，要求值的顺序保持不变，转换操作应是原址的，也就是在原始的二叉搜索树上直接修改。
 *
 * <p>返回转换后的单向链表的头节点。
 *
 * @author 梦境迷离
 * @version 1.0,2020/6/24
 */
public class Leetcode_Interview_17_12 {

    public TreeNode convertBiNode(TreeNode root) {
        TreeNode head = new TreeNode(0);
        TreeNode prev = head;
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                // ---链表处理
                node.left = null; // 当前节点左指针置空
                prev.right = node; // 前置指针右指针指向当前节点，作为链表的next指针，链表新增元素
                prev = node; // 指针后移
                // ---链表处理
                // 中序遍历进入右子树
                node = node.right;
            }
        }
        return head.right;
    }

    /**
     * 0 ms,100.00% 45.4 MB,100.00%
     *
     * <p>Java这么快，Scala超时，不科学
     *
     * @param root
     * @return
     */
    public TreeNode convertBiNode2(TreeNode root) {
        TreeNode head = new TreeNode(0);
        inorder(root, head);
        return head.right;
    }

    private TreeNode inorder(TreeNode root, TreeNode prev) {
        if (root != null) {
            prev = inorder(root.left, prev);
            root.left = null;
            prev.right = root;
            prev = root;
            prev = inorder(root.right, prev);
        }
        return prev;
    }
}
