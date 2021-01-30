/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree; /*
                                                    // Definition for a Node.
                                                    class Node {
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
                                                    };
                                                    */

class PopulatingNextRightPointersInEachNode {
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        root.next = null;
        recursive(root);
        return root;
    }

    private void recursive(Node parent) {
        if (parent == null) {
            return;
        }
        if (parent.left == null) {
            return;
        }
        parent.left.next = parent.right;
        if (parent.next != null) {
            parent.right.next = parent.next.left;
        } else {
            parent.right.next = null;
        }
        recursive(parent.left);
        recursive(parent.right);
    }
}
