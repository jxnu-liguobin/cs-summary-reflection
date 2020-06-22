/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @description 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 * @author Mr.Li
 */
public class PringTreeFromTopToBottom {
    /**
     * @description 使用非递 层序遍历二叉树：重点->访问k层，只需要得到k-1层的节点即可
     * @param root
     * @return
     */
    public static ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<TreeNode> list = new ArrayList<>();
        ArrayList<Integer> sortedList = new ArrayList<>();
        if (root == null) {
            return sortedList;
        }
        list.add(root);
        // 当前访问的节点
        int current = 0;
        // 标记某层最后一个节点的下一个位置
        int last = 1;
        while (current < list.size()) {
            // 每次重新获取新的一行的last位置
            last = list.size();
            // 当last==current 表示访问该层结束了
            while (current < last) {
                sortedList.add(list.get(current).val);
                if (list.get(current).left != null) {
                    // 左节点不为空则压入
                    list.add(list.get(current).left);
                }
                if (list.get(current).right != null) {
                    // 右节点不为空则压入
                    list.add(list.get(current).right);
                }
                // 该层节点左到右移动：当前访问节点
                current++;
            }
            // 换行 因为泛型无法打印
        }
        return sortedList;
    }

    /**
     * @description 使用队列实现层序
     * @param root
     * @return
     */
    public ArrayList<Integer> PrintFromTopToBottom2(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.isEmpty()) {
            TreeNode treeNode = queue.poll();
            if (treeNode.left != null) {
                queue.offer(treeNode.left);
            }
            if (treeNode.right != null) {
                queue.offer(treeNode.right);
            }
            list.add(treeNode.val);
        }
        return list;
    }
}
