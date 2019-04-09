package cn.edu.jxnu.leetcode;

/**
 * 142. 环形链表 II
 * <p>
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 *
 * @author 梦境迷离
 * @time 2018-09-27
 */
public class Leetcode_142_Double_Pointer {

    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            //相遇是快慢指针相等
            //新的head再次指向慢指针处时，即为入口
            if (fast == slow) {
                ListNode slow2 = head;
                while (slow2 != slow) {
                    slow = slow.next;
                    slow2 = slow2.next;
                }
                return slow;
            }
        }
        return null;
    }

}
