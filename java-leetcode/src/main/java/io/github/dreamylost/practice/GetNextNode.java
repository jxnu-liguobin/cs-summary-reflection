/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @description 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点， 同时包含指向父结点的指针。
 * @author Mr.Li
 */
public class GetNextNode {
    public TreeLinkNode getNext(TreeLinkNode pNode) {
        TreeLinkNode temp = null;
        if (pNode == null) return temp;
        if (pNode.right != null) { // 如果有右子树，则找右子树的最左节点
            // 存在兄弟
            temp = pNode.right;
            // 找到左孩子的叶子
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }
        temp = pNode;
        // 没右子树，则找第一个当前节点是父节点左孩子的节点
        while (temp.next != null && temp != temp.next.left) {
            temp = temp.next;
        }
        return temp.next;
    }
}
