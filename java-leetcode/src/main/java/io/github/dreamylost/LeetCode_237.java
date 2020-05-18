package io.github.dreamylost;

/**
 * 删除链表中的节点
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2020-03-14
 */
public class LeetCode_237 {
    public void deleteNode(ListNode node) {
        node.value = node.next.value;
        node.next = node.next.next;
    }
}
