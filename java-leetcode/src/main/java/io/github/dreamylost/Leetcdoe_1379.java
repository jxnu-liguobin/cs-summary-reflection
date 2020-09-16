/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 1379. 找出克隆二叉树中的相同节点
 *
 * <p>给你两棵二叉树，原始树 original 和克隆树 cloned，以及一个位于原始树 original 中的目标节点 target。
 *
 * <p>其中，克隆树 cloned 是原始树 original 的一个 副本 。
 *
 * <p>请找出在树 cloned 中，与 target 相同 的节点，并返回对该节点的引用（在 C/C++ 等有指针的语言中返回 节点指针，其他语言返回节点本身）。
 *
 * <p>进阶：如果树中允许出现值相同的节点，你将如何解答？
 *
 * @author 梦境迷离
 * @version 1.0, 2020/9/16
 */
public class Leetcdoe_1379 {

    /**
     * 遍历original时遍历cloned。直接遍历一个树无法解决重复元素
     *
     * <p>1 ms,100.00% 46.2 MB,70.40%
     *
     * @param original
     * @param cloned
     * @param target
     * @return
     */
    public final TreeNode getTargetCopy(
            final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == null) return null;
        if (original == target) return cloned;
        TreeNode left = getTargetCopy(original.left, cloned.left, target);
        return left == null ? getTargetCopy(original.right, cloned.right, target) : left;
    }
}
