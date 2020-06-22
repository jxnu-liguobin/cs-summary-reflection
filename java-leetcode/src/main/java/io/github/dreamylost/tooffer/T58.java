/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

/** 请实现一个函数，用来判断一颗二叉树是不是对称的。 注意，如果一个二叉树同此二叉树的镜像是同样的， 定义其为对称的。 */
public class T58 {

    boolean isSymmetrical(TreeNode pRoot) {
        if (null == pRoot) {
            return true;
        }
        return comRoot(pRoot.left, pRoot.right);
    }

    private boolean comRoot(TreeNode left, TreeNode right) {
        if (left == null) {
            return right == null;
        }
        if (right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        return comRoot(left.right, right.left) && comRoot(left.left, right.right);
    }
}
