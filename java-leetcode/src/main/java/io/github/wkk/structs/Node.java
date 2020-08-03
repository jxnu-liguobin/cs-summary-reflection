/* All Contributors (C) 2020 */
package io.github.wkk.structs;

/**
 * @author kongwiki@163.com
 * @since 2020/8/3上午9:29
 */
public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}
