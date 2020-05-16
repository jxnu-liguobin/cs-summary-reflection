package io.github.dreamylost.practice;

/**
 * @description 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。 假设输入的数组的任意两个数字都互不相同 。
 * @author Mr.Li
 */
public class VerifySquenceOfBST {
    public boolean verifySquenceOfBST(int[] sequence) {
        if (sequence.length == 0 || sequence == null) {
            return false;
        }
        return isBST(sequence, 0, sequence.length - 1);
    }

    /**
     * @description 1.从第0位开始，找到第一位比根节点大的元素，记录此位置i。在此位置之前都属于左子树（此时已经断定左子树都小于根节点）
     *     2.检查右子树是否都大于跟节点（从第i位开始，到根节点前） 3.判断左右子树是否都属于二叉搜索树。
     * @param array 二叉树数组
     * @param start 开始遍历
     * @param end 根节点
     * @return
     */
    private static boolean isBST(int[] array, int start, int end) {
        if (start > end) {
            return true;
        }
        // 找到左子树与右子树的分界点
        int index = end - 1;
        while (index > start && array[index] > array[end]) {
            index--;
        }
        for (int i = start; i < index; i++) {
            if (array[i] > array[end]) {
                return false;
            }
        }
        // 对左右子树分别进行递归遍历
        return isBST(array, start, index) && isBST(array, index + 1, end - 1);
    }
}
