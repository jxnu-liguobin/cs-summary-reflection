/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import io.github.wkk.structs.TreeNode;

/**
 * @author kongwiki@163.com
 * @since 2020/8/7上午8:58
 */
public class SameTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else if (p.val != q.val) {
            return false;
        } else {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }
}
