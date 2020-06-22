/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

/** 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。 */
public class T23 {

    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        }
        return isBST(sequence, 0, sequence.length - 1);
    }

    public boolean isBST(int[] sequence, int start, int end) {
        if (start >= end) {
            return true;
        }
        int inx = sequence[end];
        int m = start;
        // 找到分界点
        for (int i = end - 1; i >= start; i--) {
            if (sequence[i] < inx) {
                m = i;
                break;
            }
            if (i == start) {
                m = -1;
            }
        }
        // 分界点前的数据都小于根节点
        for (int i = start; i <= m; i++) {
            if (sequence[i] > inx) {
                return false;
            }
        }
        // 分界点后的数据都大于根节点
        for (int i = m + 1; i < end; i++) {
            if (sequence[i] < inx) {
                return false;
            }
        }
        // 递归判断根节点的左右子树
        return isBST(sequence, start, m) && isBST(sequence, m + 1, end - 1);
    }
}
