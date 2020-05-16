package io.github.dreamylost;

/**
 * 删除中间节点
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2020-03-19
 */
public class Interview_02_03 {
    // 将node后面的节点提前一个
    public void deleteNode(ListNode node) {
        node.value = node.next.value;
        node.next = node.next.next;
    }
}
