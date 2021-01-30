/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.linkedlist; /*
                                                    // Definition for a Node.
                                                    class Node {
                                                        public int val;
                                                        public Node prev;
                                                        public Node next;
                                                        public Node child;
                                                    };
                                                    */

import java.util.ArrayDeque;

class FlattenAMultilevelDoublyLinkedList {
    public Node flatten(Node head) {
        Node pointer = head;
        ArrayDeque<Node> nextStack = new ArrayDeque<>();
        while (true) {
            if (pointer == null) {
                break;
            } else if (pointer.next != null && pointer.child == null) {
                pointer = pointer.next;
            } else if (pointer.next != null && pointer.child != null) {
                nextStack.push(pointer.next);
                pointer.next = pointer.child;
                pointer.child = null;
                pointer.next.prev = pointer;
                pointer = pointer.next;
            } else if (pointer.next == null && pointer.child != null) {
                pointer.next = pointer.child;
                pointer.child = null;
                pointer.next.prev = pointer;
                pointer = pointer.next;
            } else if (pointer.next == null && pointer.child == null) {
                if (nextStack.isEmpty()) {
                    break;
                } else {
                    Node next = nextStack.pop();
                    pointer.next = next;
                    next.prev = pointer;
                    pointer = pointer.next;
                }
            }
        }
        return head;
    }
}
