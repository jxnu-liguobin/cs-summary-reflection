/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import io.github.wkk.structs.ListNode;
import io.github.wkk.structs.TreeNode;

/**
 * @author kongwiki@163.com
 * @since 2020/8/18上午8:46
 */
public class ConvertSortedListToBinarySearchTree {
    /** 可以按照有序数组的思路进行转换 需要添加一个按照索引位置获取节点的方法 */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        int len = getLen(head);
        return helper(head, 0, len - 1);
    }

    private TreeNode helper(ListNode head, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (left + right) / 2;
        TreeNode node = new TreeNode(findNode(mid, head).val);
        node.left = helper(head, left, mid - 1);
        node.right = helper(head, mid + 1, right);
        return node;
    }

    private ListNode findNode(int index, ListNode head) {
        ListNode node = head;
        while (index > 0) {
            node = node.next;
            index--;
        }
        return node;
    }

    private int getLen(ListNode head) {
        int n = 0;
        ListNode node = head;
        while (node != null) {
            node = node.next;
            n++;
        }
        return n;
    }
}
