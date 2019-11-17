package cn.edu.jxnu.examples.other;

import java.util.HashMap;
import java.util.Scanner;


/**
 * @author 梦境迷离
 * @time 2018年9月28日
 */
public class LRUCache {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        LRUCache l = new LRUCache(n);
        int t = 0;
        while (scanner.hasNext()) {
            String k = scanner.nextLine() + " ";
            String[] arr = k.split("\\ ");
            if (arr.length == 2) {
                l.put(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
            }
            if (arr.length == 1) {
                t = Integer.parseInt(arr[0]);
                break;
            }
        }
        Object ret = null;
        if (t != 0) {
            ret = l.get(t);
        }
        if (ret == null) {
            System.out.println(-1);
        } else {
            System.out.println(ret);
        }
    }

    int capacity;
    HashMap<Integer, Node> map = new HashMap<Integer, Node>();
    Node head = null;
    Node end = null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }

        return -1;
    }

    public void remove(Node n) {
        if (n.pre != null) {
            n.pre.next = n.next;
        } else {
            head = n.next;
        }

        if (n.next != null) {
            n.next.pre = n.pre;
        } else {
            end = n.pre;
        }

    }

    public void setHead(Node n) {
        n.next = head;
        n.pre = null;

        if (head != null)
            head.pre = n;

        head = n;

        if (end == null)
            end = head;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node old = map.get(key);
            old.value = value;
            remove(old);
            setHead(old);
        } else {
            Node created = new Node(key, value);
            if (map.size() >= capacity) {
                map.remove(end.key);
                remove(end);
                setHead(created);

            } else {
                setHead(created);
            }

            map.put(key, created);
        }
    }
}

class Node {
    int key;
    int value;
    Node pre;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}