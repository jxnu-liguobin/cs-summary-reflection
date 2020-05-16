package io.github.dreamylost.practice;

/**
 * @description 请实现两个函数，分别用来序列化和反序列化二叉树
 * @author Mr.Li
 */
public class SerializeTree {
    private int index = -1; // 节点在序列中的索引

    /**
     * @description 前序遍历，将二叉树节点的值转为字符序列，null转为“#”
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        StringBuilder builder = new StringBuilder();
        forSerialize(root, builder);
        return builder.toString();
    }

    private void forSerialize(TreeNode root, StringBuilder builder) {
        if (root == null) {
            builder.append("#,");
        } else {
            builder.append(root.val + ",");
            // 如果当前结点不是null则递归左右儿子；
            // 如果不判断当前结点是否为空，则在递归到null时出现空指针异常
            forSerialize(root.left, builder);
            forSerialize(root.right, builder);
        }
    }

    TreeNode Deserialize(String str) {
        index++;
        int length = str.length();
        if (index >= length) return null;
        String[] nodeSeq = str.split(",");
        TreeNode pNode = null;
        if (!nodeSeq[index].equals("#")) {
            pNode = new TreeNode(Integer.valueOf(nodeSeq[index]));
            pNode.left = Deserialize(str);
            pNode.right = Deserialize(str);
        }
        return pNode;
    }
}
