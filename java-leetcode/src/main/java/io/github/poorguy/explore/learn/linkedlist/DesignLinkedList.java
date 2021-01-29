/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.linkedlist;

class DesignLinkedList {

    Node head = null;
    long length = 0;

    class Node {
        int val;
        Node prev;
        Node next;

        Node(int val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    /** Initialize your data structure here. */
    public DesignLinkedList() {
        this.head = null;
        this.length = 0;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index >= length) {
            return -1;
        }
        Node p = new Node(0, null, this.head);
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        return p.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the
     * new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node node = new Node(val, null, this.head);
        if (this.head != null) {
            this.head.prev = node;
        }
        this.head = node;
        this.length++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if (this.head == null) {
            this.head = new Node(val, null, null);
            this.length++;
            return;
        }
        Node p = new Node(0, null, this.head);
        while (p.next != null) {
            p = p.next;
        }

        Node t = new Node(val, null, null);
        p.next = t;
        t.prev = p;
        this.length++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the
     * length of linked list, the node will be appended to the end of linked list. If index is
     * greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index < 0) {
            return;
        }
        if (index > this.length) {
            return;
        }

        Node p = new Node(0, null, this.head);
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        Node toAdd = new Node(val, p, p.next);
        if (index == 0) {
            toAdd.prev = null;
            this.head = toAdd;
            length++;
            return;
        }
        if (p.next != null) {
            p.next.prev = toAdd;
        }
        p.next = toAdd;
        this.length++;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0) {
            return;
        }
        if (index >= this.length) {
            return;
        }
        if (index == 0) {
            this.head = head.next;
            if (this.head != null) {
                this.head.prev = null;
            }
            this.length--;
            return;
        }

        Node p = new Node(0, null, this.head);
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        p.prev.next = p.next;
        if (p.next != null) {
            p.next.prev = p.prev;
        }
        this.length--;
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such: MyLinkedList obj = new
 * MyLinkedList(); int param_1 = obj.get(index); obj.addAtHead(val); obj.addAtTail(val);
 * obj.addAtIndex(index,val); obj.deleteAtIndex(index);
 */
