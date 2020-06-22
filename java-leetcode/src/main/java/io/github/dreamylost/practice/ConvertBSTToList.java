/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @description 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。 要求不能创建任何新的结点，只能调整树中结点指针的指向。
 * @author Mr.Li
 */
public class ConvertBSTToList {

    /** @description 作者：乔Bridge */
    private TreeNode headList = null;

    private TreeNode lastList = null;

    public TreeNode Convert3(TreeNode pRootOfTree) {
        visit(pRootOfTree);
        return headList;
    }

    // 中序遍历
    private void visit(TreeNode root) {
        if (root == null) return;
        visit(root.left);
        createList(root);
        visit(root.right);
    }

    // 建表
    private void createList(TreeNode node) {
        // 当前结点左结点连接上一个
        node.left = lastList;
        // 为空说明当前结点是第一个结点
        if (lastList == null) headList = node;
        else
            // 不为空要将当前结点赋给上个结点的右结点
            lastList.right = node;
        // 更新last
        lastList = node;
    }

    /**
     * *******************************************************************************************************
     */
    TreeNode head = null;

    TreeNode realHead = null;

    public TreeNode Convert1(TreeNode pRootOfTree) {
        ConvertSub(pRootOfTree);
        return realHead;
    }

    private void ConvertSub(TreeNode pRootOfTree) {
        if (pRootOfTree == null) return;
        ConvertSub(pRootOfTree.left);
        if (headList == null) {
            headList = pRootOfTree;
            realHead = pRootOfTree;
        } else {
            headList.right = pRootOfTree;
            pRootOfTree.left = headList;
            headList = pRootOfTree;
        }
        ConvertSub(pRootOfTree.right);
    }

    /**
     * *******************************************************************************************************
     */
    /**
     * @description 方法二：递归版 解题思路： 1.将左子树构造成双链表，并返回链表头节点。 2.定位至左子树双链表最后一个节点。
     *     3.如果左子树链表不为空的话，将当前root追加到左子树链表。 4.将右子树构造成双链表，并返回链表头节点。 5.如果右子树链表不为空的话，将该链表追加到root节点之后。
     *     6.根据左子树链表是否为空确定返回的节点。
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) return null;
        if (pRootOfTree.left == null && pRootOfTree.right == null) return pRootOfTree;
        // 1.将左子树构造成双链表，并返回链表头节点
        TreeNode left = Convert(pRootOfTree.left);
        TreeNode p = left;
        // 2.定位至左子树双链表最后一个节点
        while (p != null && p.right != null) {
            p = p.right;
        }
        // 3.如果左子树链表不为空的话，将当前root追加到左子树链表
        if (left != null) {
            p.right = pRootOfTree;
            pRootOfTree.left = p;
        }
        // 4.将右子树构造成双链表，并返回链表头节点
        TreeNode right = Convert(pRootOfTree.right);
        // 5.如果右子树链表不为空的话，将该链表追加到root节点之后
        if (right != null) {
            right.left = pRootOfTree;
            pRootOfTree.right = right;
        }
        return left != null ? left : pRootOfTree;
    }

    /**
     * *******************************************************************************************************
     */
    /** @description 解题思路： 思路与方法二中的递归版一致，仅对第2点中的定位作了修改，新增一个全局变量记录左子树的最后一个节点。 */
    // 记录子树链表的最后一个节点，终结点只可能为只含左子树的非叶节点与叶节点
    protected TreeNode leftLast = null;

    public TreeNode Convert2(TreeNode root) {
        if (root == null) return null;
        if (root.left == null && root.right == null) {
            leftLast = root; // 最后的一个节点可能为最右侧的叶节点
            return root;
        }
        // 1.将左子树构造成双链表，并返回链表头节点
        TreeNode left = Convert(root.left);
        // 3.如果左子树链表不为空的话，将当前root追加到左子树链表
        if (left != null) {
            leftLast.right = root;
            root.left = leftLast;
        }
        leftLast = root; // 当根节点只含左子树时，则该根节点为最后一个节点
        // 4.将右子树构造成双链表，并返回链表头节点
        TreeNode right = Convert(root.right);
        // 5.如果右子树链表不为空的话，将该链表追加到root节点之后
        if (right != null) {
            right.left = root;
            root.right = right;
        }
        return left != null ? left : root;
    }

    /**
     * *******************************************************************************************************
     */
    /** @DESCRIPTION 方法一：非递归版 解题思路： 1.核心是中序遍历的非递归算法。 2.修改当前遍历节点与前一遍历节点的指针指向。 */
    public TreeNode ConvertBSTToBiList(TreeNode root) {
        if (root == null) return null;
        java.util.Stack<TreeNode> stack = new java.util.Stack<TreeNode>();
        TreeNode p = root;
        TreeNode pre = null; // 用于保存中序遍历序列的上一节点
        boolean isFirst = true;
        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            if (isFirst) {
                root = p; // 将中序遍历序列中的第一个节点记为root
                pre = root;
                isFirst = false;
            } else {
                pre.right = p;
                p.left = pre;
                pre = p;
            }
            p = p.right;
        }
        return root;
    }
}
