/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack; /*
                                                    // Definition for a Node.
                                                    class Node {
                                                        public int val;
                                                        public List<Node> neighbors;
                                                        public Node() {
                                                            val = 0;
                                                            neighbors = new ArrayList<Node>();
                                                        }
                                                        public Node(int _val) {
                                                            val = _val;
                                                            neighbors = new ArrayList<Node>();
                                                        }
                                                        public Node(int _val, ArrayList<Node> _neighbors) {
                                                            val = _val;
                                                            neighbors = _neighbors;
                                                        }
                                                    }
                                                    */

import java.util.HashMap;
import java.util.Map;

/** todo worth review */
class CloneGraph {
    public Node cloneGraph(Node node) {
        Map<Node, Node> map = new HashMap<>();
        return dfs(node, map);
    }

    private Node dfs(Node node, Map<Node, Node> map) {
        if (node == null) {
            return node;
        } else if (map.containsKey(node)) {
            return map.get(node);
        } else {
            Node newNode = new Node(node.val);
            map.put(node, newNode);
            for (Node neighbor : node.neighbors) {
                newNode.neighbors.add(dfs(neighbor, map));
            }
            return newNode;
        }
    }
}
