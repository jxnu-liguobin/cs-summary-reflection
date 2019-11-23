package io.github.dreamylost;

/**
 * leetcode包通用
 *
 * @author 梦境迷离
 * @version v2.0
 * @time 2019-08-14
 */
public class ListNode {

    public int value;
    public ListNode next = null;

    public ListNode(int val) {
        this.value = val;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((next == null) ? 0 : next.hashCode());
        result = prime * result + value;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListNode other = (ListNode) obj;
        if (next == null) {
            if (other.next != null)
                return false;
        } else if (!next.equals(other.next))
            return false;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ListNode [val=" + value + ", next=" + next + "]";
    }
}