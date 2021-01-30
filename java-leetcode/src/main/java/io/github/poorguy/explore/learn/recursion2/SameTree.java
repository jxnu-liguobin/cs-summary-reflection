/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

import java.util.ArrayDeque;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class SameTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        return iterate(p, q);
    }

    private boolean recursive(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p != null && q == null) {
            return false;
        } else if (p == null && q != null) {
            return false;
        } else {
            if (p.val == q.val) {
                return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
            } else {
                return false;
            }
        }
    }

    private boolean iterate(TreeNode p, TreeNode q) {
        ArrayDeque<TreeNode> pStack = new ArrayDeque<>();
        ArrayDeque<TreeNode> qStack = new ArrayDeque<>();
        TreeNode pCurr = p;
        TreeNode qCurr = q;
        while (pCurr != null || !pStack.isEmpty()) {
            while (pCurr != null) {
                if (qCurr == null) {
                    return false;
                } else if (pCurr.val != qCurr.val) {
                    return false;
                }
                pStack.push(pCurr);
                qStack.push(qCurr);
                pCurr = pCurr.left;
                qCurr = qCurr.left;
            }
            if (pCurr == null && qCurr != null) {
                return false;
            }
            pCurr = pStack.pop();
            qCurr = qStack.pop();
            pCurr = pCurr.right;
            qCurr = qCurr.right;
        }
        if (pCurr == null && qCurr != null) {
            return false;
        }
        return true;
    }
}
