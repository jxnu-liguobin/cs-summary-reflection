/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 面试题 04.06. 后继者
 *
 * <p>设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
 *
 * <p>如果指定节点没有对应的“下一个”节点，则返回null。
 *
 * @author 梦境迷离
 * @version 1.0
 * @see [[https://github.com/jxnu-liguobin]]
 * @since 2020-08-16
 */
public class Interview_04_06 {

    public static void main(String[] args) {

        TreeNode node2 = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node3 = new TreeNode(3);

        node2.left = node1;
        node2.right = node3;

        TreeNode ret = new Interview_04_06().inorderSuccessor(node2, node1);
        System.out.println(ret.val);
    }

    // 3 ms,100.00%
    // 40.2 MB,89.85%
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }
        if (root.val <= p.val) {
            return inorderSuccessor(root.right, p);
        }
        TreeNode node = inorderSuccessor(root.left, p);
        return node == null ? root : node;
    }
}
