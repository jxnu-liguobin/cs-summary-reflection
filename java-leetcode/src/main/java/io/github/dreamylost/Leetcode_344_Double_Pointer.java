/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 编写一个函数，其作用是将输入的字符串反转过来。
 *
 * @author 梦境迷离
 * @time 2018-09-20
 */
public class Leetcode_344_Double_Pointer {

    public String reverseString(String s) {
        char[] word = s.toCharArray();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            char temp = word[i];
            word[i] = word[j];
            word[j] = temp;
            i++;
            j--;
        }
        return new String(word);
    }
}
