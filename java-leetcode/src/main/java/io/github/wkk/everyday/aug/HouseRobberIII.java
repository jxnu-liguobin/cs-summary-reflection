/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import io.github.wkk.structs.TreeNode;
import java.util.*;

/**
 * @author kongwiki@163.com
 * @since 2020/8/5上午10:06
 */
public class HouseRobberIII {
    Map<TreeNode, Integer> f = new HashMap<TreeNode, Integer>();
    Map<TreeNode, Integer> g = new HashMap<TreeNode, Integer>();

    public int rob(TreeNode root) {
        dfs(root);
        return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        dfs(node.right);
        f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
        g.put(
                node,
                Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0))
                        + Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
    }

    public int robII(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 层次遍历
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode p = root;
        queue.offer(p);
        List<Integer> res = new ArrayList<>();
        res.add(root.val);
        int count = 0;
        int cur = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int temp = 0;
            count++;
            for (int i = 0; i < size; i++) {
                p = queue.poll();
                temp += p.val;
                if (p.left != null) {
                    queue.offer(p.left);
                }
                if (p.right != null) {
                    queue.offer(p.right);
                }
            }
            if (count == 2) {
                cur = Math.max(temp, root.val);
                res.add(cur);
            } else if (count > 2) {
                cur = Math.max(temp + res.get(res.size() - 2), res.get(res.size() - 1));
                res.add(cur);
            }
        }
        return res.get(res.size() - 1);
    }
}
