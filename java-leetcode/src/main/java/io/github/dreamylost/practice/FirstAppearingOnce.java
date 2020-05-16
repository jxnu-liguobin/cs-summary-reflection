package io.github.dreamylost.practice;

/**
 * @description 题目描述 请实现一个函数用来找出字符流中第一个只出现一次的字符。 例如，当从字符流中只读出前两个字符"go"时， 第一个只出现一次的字符是"g"。
 *     当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。 输出描述: 如果当前字符流没有存在出现一次的字符， 返回#字符
 * @author Mr.Li
 */
public class FirstAppearingOnce {
    private int count[] = new int[256];
    private int index = 1;

    public void insert(char ch) {

        if (count[ch] == 0) {
            count[ch] = index++;
        } else {
            // 第二次出现的字符
            count[ch] = -1;
        }
    }

    public char firstAppearingOnce() {
        int temp = Integer.MAX_VALUE;
        char ch = '#';
        for (int i = 0; i < 256; i++) {
            // 之后在数组中找到>0的最小值，该数组下标对应的字符为所求
            if (count[i] != 0 && count[i] != -1 && count[i] < temp) {
                temp = count[i];
                ch = (char) i;
            }
        }
        return ch;
    }
}
