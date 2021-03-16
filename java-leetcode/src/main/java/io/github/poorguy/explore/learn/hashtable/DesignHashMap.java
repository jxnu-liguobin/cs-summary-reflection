/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import io.github.poorguy.util.LeetcodeRunner;
import java.util.ArrayList;
import java.util.List;

public class DesignHashMap {
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
    public DesignHashMap() {
        this.list = new Node[16];
        this.size = 0;
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        Node node = findNode(indexOf(key), key);
        if (node != null) {
            node.value = value;
            return;
        }

        // add new node
        if (size + 1 > list.length * DEFAULT_LOAD_FACTOR) {
            expend();
        }
        addNode(this.list, indexOf(key), new Node(key, value, null));
        size++;
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping
     * for the key
     */
    public int get(int key) {
        Node node = findNode(indexOf(key), key);
        return node == null ? -1 : node.value;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
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

    public static void main(String[] args) throws Exception {
        String functionStrings =
                "[\"DesignHashMap\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"remove\",\"put\",\"remove\",\"put\",\"remove\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"remove\",\"put\",\"remove\",\"get\",\"remove\",\"put\",\"get\",\"put\",\"put\",\"remove\",\"put\",\"put\",\"get\",\"get\",\"put\",\"put\",\"remove\",\"put\",\"put\",\"remove\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"remove\",\"get\",\"remove\",\"put\",\"put\",\"put\",\"put\",\"remove\",\"get\",\"put\",\"put\",\"remove\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"get\",\"put\",\"put\",\"put\",\"put\",\"get\",\"remove\",\"remove\",\"put\",\"put\",\"remove\",\"get\",\"put\",\"get\",\"remove\",\"remove\",\"put\",\"put\",\"put\",\"put\"]";
        String paramStrings =
                "[[],[74,64],[20,48],[2,48],[2],[99,78],[29,66],[99],[43,38],[28],[63,9],[2],[88,26],[50,28],[43],[7,7],[31,84],[23,77],[53,60],[71,49],[28,23],[19,74],[98,72],[71],[77,45],[25,67],[68,44],[68,88],[48],[8,21],[35,86],[43],[52,89],[63],[63],[23],[72,91],[28],[26,10],[12,25],[92],[34,61],[76,99],[98],[68],[28,60],[60,16],[34],[30,98],[50,79],[50],[26,25],[2],[26,73],[47,52],[49,13],[28,95],[77,64],[5],[83,75],[25,10],[44],[36],[68],[35,53],[25,59],[60,9],[19,46],[5],[29],[11,32],[31,24],[16],[72,78],[88,63],[43,69],[69],[56,4],[89,28],[26,58],[28,22],[62],[76,57],[64,73],[93,94],[17,82],[81],[86],[70],[83,36],[50,80],[17],[63],[93,10],[17],[74],[54],[39,11],[98,34],[46,58],[68,0]]";
        String expectedString =
                "[null,null,null,null,48,null,null,null,null,null,null,null,null,null,38,null,null,null,null,null,null,null,null,49,null,null,null,null,-1,null,null,null,null,null,-1,null,null,23,null,null,null,null,null,72,88,null,null,null,null,null,null,null,-1,null,null,null,null,null,-1,null,null,null,-1,null,null,null,null,null,null,66,null,null,null,null,null,null,-1,null,null,null,null,-1,null,null,null,null,-1,null,null,null,null,null,-1,null,-1,null,null,null,null,null,null]";
        LeetcodeRunner<DesignHashMap, List<String>, List<List<Integer>>, List<Integer>> runner =
                new LeetcodeRunner<>();
        runner.run(DesignHashMap.class, functionStrings, paramStrings, expectedString);
    }
}
