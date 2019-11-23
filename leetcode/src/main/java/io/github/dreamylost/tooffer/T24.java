package io.github.dreamylost.tooffer;

import java.util.ArrayList;

/**
 * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 */
public class T24 {

    private ArrayList<ArrayList<Integer>> listAll = new ArrayList<>();
    private ArrayList<Integer> lists = new ArrayList<>();

    /**
     * 思路：
     * 递归先序遍历树， 把结点加入路径。
     * 若该结点是叶子结点则比较当前路径和是否等于期待和。
     * 弹出结点，每一轮递归返回到父结点时，当前路径也应该回退一个结点
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        if (root == null) {
            return listAll;
        }
        lists.add(root.val);
        target -= root.val;
        if (target == 0 && root.left == null && root.right == null) {
            listAll.add(new ArrayList<>(lists));
        }
        FindPath(root.left, target);
        FindPath(root.right, target);
        lists.remove(lists.size() - 1);
        return listAll;
    }
}
