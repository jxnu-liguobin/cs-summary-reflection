/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
 * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
 */
class FindDuplicateSubtrees {
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();

        Map<String, Integer> map = new HashMap<>();
        serialized(root, map, result);
        result.toArray(new TreeNode[0]);

        return result;
    }

    private String serialized(TreeNode node, Map<String, Integer> map, List<TreeNode> result) {
        if (node == null) {
            return "#";
        }

        String serializedStr =
                node.val
                        + ","
                        + serialized(node.left, map, result)
                        + ","
                        + serialized(node.right, map, result);
        if (map.containsKey(serializedStr)) {
            Integer count = map.get(serializedStr);
            if (count == 1) {
                result.add(node);
            }
            map.put(serializedStr, ++count);
        } else {
            map.put(serializedStr, 1);
        }
        return serializedStr;
    }
}
