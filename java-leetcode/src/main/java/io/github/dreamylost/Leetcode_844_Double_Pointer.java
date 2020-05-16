package io.github.dreamylost;

/**
 * 给定 S 和 T 两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。 # 代表退格字符。
 *
 * <p>输入：S = "ab#c", T = "ad#c" 输出：true 解释：S 和 T 都会变成 “ac”。
 *
 * @author 梦境迷离
 * @time 2018-09-16
 */
public class Leetcode_844_Double_Pointer {

    /**
     * #可以跳过一个字符
     *
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {

        for (int i = S.length() - 1, j = T.length() - 1; ; i--, j--) {
            for (int b = 0; i >= 0 && (b > 0 || S.charAt(i) == '#'); --i) {
                b += S.charAt(i) == '#' ? 1 : -1;
            }
            for (int b = 0; j >= 0 && (b > 0 || T.charAt(j) == '#'); --j) {
                b += T.charAt(j) == '#' ? 1 : -1;
            }
            if (i < 0 || j < 0 || S.charAt(i) != T.charAt(j)) {
                return i == -1 && j == -1;
            }
        }
    }

    public boolean backspaceCompare2(String S, String T) {
        int i = S.length() - 1, j = T.length() - 1;
        while (true) {
            for (int back = 0; i >= 0 && (back > 0 || S.charAt(i) == '#'); --i) {
                back += S.charAt(i) == '#' ? 1 : -1;
            }
            for (int back = 0; j >= 0 && (back > 0 || T.charAt(j) == '#'); --j) {
                back += T.charAt(j) == '#' ? 1 : -1;
            }
            if (i >= 0 && j >= 0 && S.charAt(i) == T.charAt(j)) {
                i--;
                j--;
            } else return i == -1 && j == -1;
        }
    }
}
