package io.github.dreamylost.tooffer;

/** 请实现两个函数，分别用来序列化和反序列化二叉树 */
public class T61 {

    String Serialize(TreeNode root) {
        if (null == root) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        Serialize2(root, stringBuffer);
        return stringBuffer.toString();
    }

    void Serialize2(TreeNode root, StringBuffer stringBuffer) {
        if (null == root) {
            stringBuffer.append("#,");
            return;
        }
        stringBuffer.append(root.val);
        stringBuffer.append(",");
        Serialize2(root.left, stringBuffer);
        Serialize2(root.right, stringBuffer);
    }

    int index = -1;

    TreeNode Deserialize(String str) {
        if (str.length() == 0) {
            return null;
        }
        String[] strings = str.split(",");
        return Deserialize2(strings);
    }

    TreeNode Deserialize2(String[] strings) {
        index++;
        if (!strings[index].equals("#")) {
            TreeNode root = new TreeNode(0);
            root.val = Integer.parseInt(strings[index]);
            root.left = Deserialize2(strings);
            root.right = Deserialize2(strings);
            return root;
        }
        return null;
    }
}
