/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * @description 模拟树的结点
 * @author Mr.Li
 */
public class TreeNode {

    public int value = 0;
    public TreeNode left = null;

    @Override
    public String toString() {
        return "TreeNode{" + "value=" + value + ", left=" + left + ", right=" + right + '}';
    }

    public TreeNode right = null;

    public TreeNode(int val) {
        this.value = val;
    }
}
