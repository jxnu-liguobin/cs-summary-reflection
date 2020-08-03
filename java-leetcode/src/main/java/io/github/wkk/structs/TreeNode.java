/* All Contributors (C) 2020 */
package io.github.wkk.structs;

/**
 * @author kongwiki@163.com
 * @since 2020/8/3上午9:29
 */
public class TreeNode {
    public int val;
    public int count;
    public TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.count = 0;
    }

    public TreeNode() {}
}
