/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

import java.util.*;

/**
 * 剑指 Offer 68 - II. 二叉树的最近公共祖先
 *
 * @author 梦境迷离 dreamylost
 * @version v1.0
 * @since 2020-06-26
 */
public class Leetcode_JZ_68_2 {

    /**
     * 7 ms,100.00% 41.8 MB,100.00%
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //当p或q本身就是根节点时，公共就是p或q本身，因为根之上没有父节点
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null && right == null) {
            return null;
        }
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }

    /**
     * author 2hp 暴力 7 ms,100.00% 40.9 MB,100.00%
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        //如果从p开始向下找有q则返回p
        if (dfs(p, q)) return p;
        //如果从q开始向下找有p返回q
        if (dfs(q, p)) return q;
        //否则
        return help(root, p, q);
    }

    //方法：在root向下寻找是否有search节点
    public boolean dfs(TreeNode root, TreeNode search) {
        if (root == null) return false;
        if (root.val == search.val) return true;
        else return dfs(root.left, search) || dfs(root.right, search);
    }

    //方法：1.root子节点中分别含有p、q
    //2.root单边含有p、q
    public TreeNode help(TreeNode root, TreeNode p, TreeNode q) {
        //如果root.left下有p并且root.right下有q返回root；相反也是一样返回root
        if ((dfs(root.left, p) && dfs(root.right, q)) || dfs(root.left, q) && dfs(root.right, p)) {
            return root;
        } else if (dfs(root.left, p) && dfs(root.left, q))
            return help(root.left, p, q); //否则p,q就是在同一边，递归调用即可
        else return help(root.right, p, q);
    }

    /**
     * author sdwwld
     *
     * <p>15 ms,7.16% 40 MB,100.00%
     *
     * <p>非递归 BFS
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        //记录遍历到的每个节点的父节点
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        parentMap.put(root, null);
        queue.add(root);
        //直到两个节点都找到为止
        while (!parentMap.containsKey(p) || !parentMap.containsKey(q)) {
            TreeNode node = queue.poll();
            if (node != null && node.left != null) {
                parentMap.put(node.left, node);
                queue.add(node.left);
            }
            if (node != null && node.right != null) {
                parentMap.put(node.right, node);
                queue.add(node.right);
            }
        }

        Set<TreeNode> ancestorsSet = new HashSet<>();
        //记录下p和他的祖先节点，从p节点开始一直到根节点
        while (p != null) {
            ancestorsSet.add(p);
            p = parentMap.get(p);
        }
        //查看p和他的祖先节点是否包含q节点，如果不包含再看是否包含q的父节点
        while (!ancestorsSet.contains(q)) q = parentMap.get(q);
        return q;
    }
}
