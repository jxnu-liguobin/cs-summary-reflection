package io.github.dreamylost.practice;

/**
 * @description 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 * @author Mr.Li
 */
public class IsHasSubtree {
    /**
     * @description 第一种方法：使用标记位
     * @param root1
     * @param root2
     * @return
     */
    public boolean hasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return false;
        }
        boolean result = false;
        // 当Tree1和Tree2都不为零的时候，才进行比较。否则直接返回false
        if (root2 != null && root1 != null) {
            // 如果找到了对应Tree2的根节点的点
            if (root1.val == root2.val) {
                // 以这个根节点为为起点判断是否包含Tree2
                result = doesTree1HaveTree2(root1, root2);
            }
            // 如果找不到，那么就再去root的左儿子当作起点，去判断时候包含Tree2
            if (!result) {
                result = hasSubtree(root1.left, root2);
            }

            // 如果还找不到，那么就再去root的右儿子当作起点，去判断时候包含Tree2
            if (!result) {
                result = hasSubtree(root1.right, root2);
            }
        }
        // 返回结果
        return result;
    }

    private boolean doesTree1HaveTree2(TreeNode node1, TreeNode node2) {
        // 如果Tree2已经遍历完了都能对应的上，返回true
        if (node2 == null) {
            return true;
        }
        // 如果Tree2还没有遍历完，Tree1却遍历完了。返回false
        if (node1 == null) {
            return false;
        }
        // 如果其中有一个点没有对应上，返回false
        if (node1.val != node2.val) {
            return false;
        }
        // 如果根节点对应的上，那么就分别去子节点里面匹配
        return doesTree1HaveTree2(node1.left, node2.left)
                && doesTree1HaveTree2(node1.right, node2.right);
    }

    /**
     * @description 第二种：不使用标记
     * @param node1
     * @param node2
     * @return
     */
    private boolean isSubtree(TreeNode node1, TreeNode node2) {
        if (node2 == null) {
            return true;
        }
        if (node1 == null) {
            return false;
        }
        // 根相等->继续比较左右子结点
        if (node2.val == node1.val) {
            return isSubtree(node1.left, node2.left) && isSubtree(node1.right, node2.right);
        } else {
            return false;
        }
    }

    public boolean HasSubtree2(TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) {
            return false;
        }
        // 短路特性
        // 根不相等->使用主树的左孩子进行比较||使用主树的右孩子进比较
        return isSubtree(node1, node2)
                || hasSubtree(node1.left, node2)
                || hasSubtree(node1.right, node2);
    }
}
