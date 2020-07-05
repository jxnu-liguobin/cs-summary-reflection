/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 剑指 Offer 68 - I. 二叉搜索树的最近公共祖先
 *
 * @author 梦境迷离 dreamylost
 * @version v1.0
 * @since 2020-06-25
 */
public class Leetcode_JZ_68_1 {

    /**
     * 6 ms,100.00% 41.4 MB,100.00%
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else {
            return root;
        }
    }
}
