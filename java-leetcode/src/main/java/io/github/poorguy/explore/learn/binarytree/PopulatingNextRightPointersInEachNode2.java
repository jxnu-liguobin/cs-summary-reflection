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

class PopulatingNextRightPointersInEachNode2 {
    public Node connect(Node root) {
        Node curr = root;
        // start of next line
        Node nextLevel = null;
        // the before element of the same line
        Node before = null;

        // all line
        while (curr != null) {
            // if nextLevel changed
            boolean changed = false;
            // one line
            while (curr != null) {
                if (curr.left != null && curr.right != null) {
                    if (before == null) {
                        nextLevel = curr.left;
                        changed = true;
                    } else {
                        before.next = curr.left;
                    }
                    curr.left.next = curr.right;
                    before = curr.right;
                } else if (curr.left != null && curr.right == null) {
                    if (before == null) {
                        nextLevel = curr.left;
                        changed = true;
                    } else {
                        before.next = curr.left;
                    }
                    before = curr.left;
                } else if (curr.left == null && curr.right != null) {
                    if (before == null) {
                        nextLevel = curr.right;
                        changed = true;
                    } else {
                        before.next = curr.right;
                    }
                    before = curr.right;
                }
                curr = curr.next;
            }

            if (!changed) {
                break;
            }
            // new line
            curr = nextLevel;
            before = null;
        }

        return root;
    }
}
