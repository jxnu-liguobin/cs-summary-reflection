/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarytree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
 * right; TreeNode(int x) { val = x; } }
 */
public class SerializeAndDeserializeBinaryTree {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode curr = root;
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                sb.append(curr.val + ",");
                stack.push(curr);
                curr = curr.left;
            }
            sb.append("#,");
            curr = stack.pop();
            curr = curr.right;
        }
        int length = sb.length();
        return sb.toString().substring(0, length - 1);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        List<String> nodeStringList = new LinkedList<String>(Arrays.asList(data.split(",")));
        return helper(nodeStringList);
    }

    private TreeNode helper(List<String> nodeList) {
        if (nodeList.size() == 0) {
            return null;
        }
        if (nodeList.get(0).equals("#")) {
            nodeList.remove(0);
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(nodeList.get(0)));
        nodeList.remove(0);
        node.left = helper(nodeList);
        node.right = helper(nodeList);
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
