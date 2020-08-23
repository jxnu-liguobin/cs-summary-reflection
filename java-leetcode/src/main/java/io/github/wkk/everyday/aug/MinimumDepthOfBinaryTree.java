/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

import io.github.wkk.structs.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author kongwiki@163.com
 * @since 2020/8/21上午9:22
 */
public class MinimumDepthOfBinaryTree {
    // 层次遍历
    // 当遇到左右孩子均为空的时候, 即为最小深度
    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode p = root;
        queue.offer(p);
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            count++;
            for (int i = 0; i < size; i++) {
                p = queue.poll();
                if (p.right == null && p.left == null) {
                    return count;
                }
                if (p.left != null) {
                    queue.offer(p.left);
                }
                if (p.right != null) {
                    queue.offer(p.right);
                }
            }
        }
        return 0;
    }
}
