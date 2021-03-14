/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

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
        int exist = get(key);
        if (exist != -1) {
            Node pointer = list[indexOf(key)];
            while (pointer != null && pointer.key != key) {
                pointer = pointer.next;
            }
            assert pointer != null;
            pointer.value = value;
            return;
        }

        // add new node
        if (size + 1 > list.length * DEFAULT_LOAD_FACTOR) {
            expend();
        }

        int index = indexOf(key);
        Node node = list[index];
        if (node == null) {
            list[index] = new Node(key, value, null);
        } else {
            Node pointer = node;
            while (pointer.next != null) {
                pointer = pointer.next;
            }
            pointer.next = new Node(key, value, null);
        }
        size++;
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping
     * for the key
     */
    public int get(int key) {
        int index = indexOf(key);
        Node pointer = list[index];
        while (pointer != null && pointer.key != key) {
            pointer = pointer.next;
        }

        if (pointer == null) {
            return -1;
        }
        return pointer.value;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int index = indexOf(key);
        Node pointer = list[index];
        if (pointer == null) {
            return;
        }
        Node next = pointer.next;
        if (next == null && pointer.key == key) {
            list[index] = null;
            size--;
            return;
        }
        while (next != null) {
            if (next.key == key) {
                pointer.next = next.next;
                size--;
                return;
            }
            pointer = pointer.next;
            next = next.next;
        }
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
            if (node != null) {
                int newIndex = indexOf(node.key, newList.length);
                newList[newIndex] = node;
                Node pointer = node;
                while (pointer.next != null) {
                    pointer = pointer.next;
                    int newIndex2 = indexOf(pointer.key, newList.length);
                    newList[newIndex2] = pointer;
                }
            }
        }
        this.list = newList;
    }

    public static void main(String[] args) {
        DesignHashMap map = new DesignHashMap();
        String[] methods = {
            "remove", "put", "remove", "remove", "get", "remove", "put", "get", "remove", "put",
            "put", "put", "put", "put", "put", "put", "put", "put", "put", "put", "remove", "put",
            "put", "get", "put", "get", "put", "put", "get", "put", "remove", "remove", "put",
            "put", "get", "remove", "put", "put", "put", "get", "put", "put", "remove", "put",
            "remove", "remove", "remove", "put", "remove", "get", "put", "put", "put", "put",
            "remove", "put", "get", "put", "put", "get", "put", "remove", "get", "get", "remove",
            "put", "put", "put", "put", "put", "put", "get", "get", "remove", "put", "put", "put",
            "put", "get", "remove", "put", "put", "put", "put", "put", "put", "put", "put", "put",
            "put", "remove", "remove", "get", "remove", "put", "put", "remove", "get", "put", "put"
        };
        int[][] params = {
            {27}, {65, 65}, {19}, {0}, {18}, {3}, {42, 0}, {19}, {42}, {17, 90}, {31, 76}, {48, 71},
            {5, 50}, {7, 68}, {73, 74}, {85, 18}, {74, 95}, {84, 82}, {59, 29}, {71, 71}, {42},
            {51, 40}, {33, 76}, {17}, {89, 95}, {95}, {30, 31}, {37, 99}, {51}, {95, 35}, {65},
            {81}, {61, 46}, {50, 33}, {59}, {5}, {75, 89}, {80, 17}, {35, 94}, {80}, {19, 68},
            {13, 17}, {70}, {28, 35}, {99}, {37}, {13}, {90, 83}, {41}, {50}, {29, 98}, {54, 72},
            {6, 8}, {51, 88}, {13}, {8, 22}, {85}, {31, 22}, {60, 9}, {96}, {6, 35}, {54}, {15},
            {28}, {51}, {80, 69}, {58, 92}, {13, 12}, {91, 56}, {83, 52}, {8, 48}, {62}, {54}, {25},
            {36, 4}, {67, 68}, {83, 36}, {47, 58}, {82}, {36}, {30, 85}, {33, 87}, {42, 18},
            {68, 83}, {50, 53}, {32, 78}, {48, 90}, {97, 95}, {13, 8}, {15, 7}, {5}, {42}, {20},
            {65}, {57, 9}, {2, 41}, {6}, {33}, {16, 44}, {95, 30}
        };
        Integer[] expectedResult = {
            null, null, null, null, -1, null, null, -1, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, 90, null, -1, null, null, 40, null,
            null, null, null, null, 29, null, null, null, null, 17, null, null, null, null, null,
            null, null, null, null, 33, null, null, null, null, null, null, 18, null, null, -1,
            null, null, -1, 35, null, null, null, null, null, null, null, -1, -1, null, null, null,
            null, null, -1, null, null, null, null, null, null, null, null, null, null, null, null,
            null, -1, null, null, null, null, 87, null, null
        };
        for (int i = 0; i < methods.length; i++) {
            switch (methods[i]) {
                case "remove":
                    map.remove(params[i][0]);
                    break;
                case "put":
                    map.put(params[i][0], params[i][1]);
                    break;
                case "get":
                    Integer val = map.get(params[i][0]);
                    if (!val.equals(expectedResult[i])) {
                        System.out.println(i + " ");
                    }
                    break;
            }
        }
    }
}
