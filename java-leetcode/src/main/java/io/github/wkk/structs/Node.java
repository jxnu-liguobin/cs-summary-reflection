/* All Contributors (C) 2020 */
package io.github.wkk.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kongwiki@163.com
 * @since 2020/8/12上午10:19
 */
public class Node {
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
