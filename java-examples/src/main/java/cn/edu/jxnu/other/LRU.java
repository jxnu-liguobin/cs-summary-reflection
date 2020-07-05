/* All Contributors (C) 2020 */
package cn.edu.jxnu.other;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 以下是一个基于 双向队列 + HashMap 的 LRU 算法实现，对算法的解释如下：
 *
 * <p>最基本的思路是当访问某个节点时，将其从原来的位置删除，并重新插入到链表头部 这样就能保证链表尾部存储的就是最近最久未使用的节点，当节点数量大于缓存最大空间时就删除链表尾部的节点。
 * 为了使删除操作时间复杂度为 O(1)，那么就不能采用遍历的方式找到某个节点。HashMap 存储这 Key 到节点的映射，通过 Key 就能以 O(1) 的时间得到节点，然后再以 O(1)
 * 的时间将其从双向队列中删除。
 *
 * @author 梦境迷离
 * @version v1.0
 * @time 2018年8月1日
 */
public class LRU<K, V> implements Iterable<K> {

    private Node head;
    private Node tail;
    private HashMap<K, Node> map;
    private int maxSize;

    private class Node {

        Node pre;
        Node next;
        K k;
        V v;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    public LRU(int maxSize) {

        this.maxSize = maxSize;
        this.map = new HashMap<>(maxSize);

        head = new Node(null, null);
        tail = new Node(null, null);

        head.next = tail;
        tail.pre = head;
    }

    public V get(K key) {

        if (!map.containsKey(key)) {
            return null;
        }

        Node node = map.get(key);
        unlink(node);
        appendHead(node);

        return node.v;
    }

    public void put(K key, V value) {

        if (map.containsKey(key)) {
            Node node = map.get(key);
            unlink(node);
        }

        Node node = new Node(key, value);
        map.put(key, node);
        appendHead(node);

        if (map.size() > maxSize) {
            Node toRemove = removeTail();
            map.remove(toRemove);
        }
    }

    private void unlink(Node node) {
        Node pre = node.pre;
        node.pre = node.next;
        node.next = pre;
    }

    private void appendHead(Node node) {
        node.next = head.next;
        head.next = node;
    }

    private Node removeTail() {
        Node node = tail.pre;
        node.pre = tail;
        return node;
    }

    @Override
    public Iterator<K> iterator() {

        return new Iterator<K>() {

            private Node cur = head.next;

            @Override
            public boolean hasNext() {
                return cur != tail;
            }

            @Override
            public K next() {
                Node node = cur;
                cur = cur.next;
                return node.k;
            }
        };
    }
}
