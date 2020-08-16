/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 面试题 04.08. 首个共同祖先
 *
 * <p>设计并实现一个算法，找出二叉树中某两个节点的第一个共同祖先。不得将其他的节点存储在另外的数据结构中。注意：这不一定是二叉搜索树。
 *
 * <p>例如，给定如下二叉树: root = [3,5,1,6,2,0,8,null,null,7,4]
 *
 * @author 梦境迷离
 * @version 1.0
 * @see [[https://github.com/jxnu-liguobin]]
 * @since 2020-08-16
 */
public class Interview_04_08 {

    /**
     * 这题限制了不能使用额外空间 8 ms,58.55% 41.8 MB,90.87% {@code Leetcode_236.scala}、{@code
     * Leetcode_JZ_68_2.java}
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if ((root == p) || (root == q)) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        if (left == null && right == null) return null;
        return left == null ? right : left;
    }
}
