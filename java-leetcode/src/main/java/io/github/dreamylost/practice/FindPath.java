/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.ArrayList;

/**
 * @description 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径 。
 * @author Mr.Li
 */
public class FindPath {
    /**
     * @description 使用辅助函数递归
     * @param root
     * @param target
     * @return
     */
    public ArrayList<ArrayList<Integer>> findPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
        if (root == null) return arr;
        ArrayList<Integer> a = new ArrayList<Integer>();
        int sum = 0;
        forFindPath(root, target, arr, a, sum);
        return arr;
    }

    private void forFindPath(
            TreeNode root,
            int target,
            ArrayList<ArrayList<Integer>> arr,
            ArrayList<Integer> a,
            int sum) {
        if (root == null) {
            return;
        }
        sum += root.val;
        if (root.left == null && root.right == null) {
            if (sum == target) {
                a.add(root.val);
                arr.add(new ArrayList<Integer>(a));
                a.remove(a.size() - 1);
            }
            return;
        }
        a.add(root.val);
        forFindPath(root.left, target, arr, a, sum);
        forFindPath(root.right, target, arr, a, sum);
        a.remove(a.size() - 1);
    }

    private ArrayList<ArrayList<Integer>> listAll = new ArrayList<ArrayList<Integer>>();
    private ArrayList<Integer> list = new ArrayList<Integer>();

    /**
     * @description 递归
     * @param root
     * @param target
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindPath2(TreeNode root, int target) {
        if (root == null) return listAll;
        list.add(root.val);
        target -= root.val;
        if (target == 0 && root.left == null && root.right == null)
            listAll.add(new ArrayList<Integer>(list));
        if (root.left != null) findPath(root.left, target);
        if (root.right != null) findPath(root.right, target);
        list.remove(list.size() - 1); // 深度遍历回溯到前一个节点
        return listAll;
    }
}
