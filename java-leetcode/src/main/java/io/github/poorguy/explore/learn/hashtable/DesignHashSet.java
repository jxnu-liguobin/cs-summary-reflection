/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.ArrayList;
import java.util.List;

class DesignHashSet {
    private class Node {
        int key;
        int value;
        Node next;

        public Node(int key, int value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node[] list;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int size;

    /** Initialize your data structure here. */
    public DesignHashSet() {
        this.list = new Node[16];
        this.size = 0;
    }

    public void add(int key) {
        Node node = findNode(indexOf(key), key);
        if (node != null) {
            node.value = key;
            return;
        }

        // add new node
        if (size + 1 > list.length * DEFAULT_LOAD_FACTOR) {
            expend();
        }
        addNode(this.list, indexOf(key), new Node(key, key, null));
        size++;
    }

    public void remove(int key) {
        int index = indexOf(key);
        Node pointer = new Node(-1, -1, list[index]);
        Node head = pointer;
        while (pointer.next != null) {
            if (pointer.next.key == key) {
                pointer.next = pointer.next.next;
                size--;
                break;
            }
            pointer = pointer.next;
        }
        this.list[index] = head.next;
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        Node node = findNode(indexOf(key), key);
        return node != null;
    }

    private int indexOf(int key) {
        return (key ^ (key >>> 16)) & (list.length - 1);
    }

    private int indexOf(int key, int listLength) {
        return (key ^ (key >>> 16)) & (listLength - 1);
    }

    private void expend() {
        Node[] newList = new Node[list.length * 2];
        for (Node node : list) {
            if (node == null) {
                continue;
            }

            List<Node> nodeToAdd = new ArrayList<>();
            Node pointer = node;
            while (pointer != null) {
                Node next = pointer.next;
                pointer.next = null;
                nodeToAdd.add(pointer);
                pointer = next;
            }
            for (Node toAdd : nodeToAdd) {
                int index = indexOf(toAdd.key, newList.length);
                addNode(newList, index, toAdd);
            }
        }
        this.list = newList;
    }

    private void addNode(Node[] list, int index, Node node) {
        if (list[index] == null) {
            list[index] = node;
        } else {
            addNode(list[index], node);
        }
    }

    private void addNode(Node before, Node toAdd) {
        if (before.next == null) {
            before.next = toAdd;
        } else {
            addNode(before.next, toAdd);
        }
    }

    private Node findNode(int index, int key) {
        return findNode(this.list[index], key);
    }

    private Node findNode(Node node, int key) {
        if (node == null) {
            return null;
        }

        if (node.key == key) {
            return node;
        } else {
            return findNode(node.next, key);
        }
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such: MyHashSet obj = new MyHashSet();
 * obj.add(key); obj.remove(key); boolean param_3 = obj.contains(key);
 */
