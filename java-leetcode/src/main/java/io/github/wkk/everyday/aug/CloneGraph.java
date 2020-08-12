/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import io.github.wkk.structs.Node;
import java.util.*;

/**
 * 克隆图 类似于复杂链表的复制
 *
 * <p>使用一个Map做映射即可
 *
 * @author kongwiki@163.com
 * @since 2020/8/12上午8:58
 */
public class CloneGraph {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        Node p = node;
        queue.offer(p);
        Node clone = new Node(p.val, new ArrayList<>());
        Map<Node, Node> map = new HashMap<>();
        map.put(p, clone);
        while (!queue.isEmpty()) {
            p = queue.poll();
            for (Node neiNode : p.neighbors) {
                if (!map.containsKey(neiNode)) {
                    map.put(neiNode, new Node(neiNode.val, new ArrayList<>()));
                    queue.offer(neiNode);
                }
                map.get(p).neighbors.add(map.get(neiNode));
            }
        }
        return clone;
    }
}
