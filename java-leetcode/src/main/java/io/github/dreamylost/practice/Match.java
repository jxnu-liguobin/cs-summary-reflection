/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @description 请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次 （包含0次）。
 *     在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配， 但是与"aa.a"和"ab*a"均不匹配
 * @author Mr.Li 不会
 */
public class Match {
    public boolean match(char[] str, char[] pattern) {
        return matchTwo(str, 0, str.length, pattern, 0, pattern.length);
    }
    // 当模式中的第二个字符不是“*”时：
    // 1、如果字符串第一个字符和模式中的第一个字符相匹配，那么字符串和模式都后移一个字符，然后匹配剩余的。
    // 2、如果 字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。

    private boolean matchTwo(char[] str, int i, int length1, char[] pattern, int j, int length2) {
        if (i == length1 && j == length2) {
            return true;
        }
        if (i == length1 && j != length2) {
            while (j != length2) {
                if (pattern[j] != '*' && (j + 1 >= length2 || pattern[j + 1] != '*')) {
                    return false;
                }
                j++;
            }
            return true;
        }
        if (i != length1 && j == length2) {
            return false;
        }
        if (j + 1 == length2) {
            if (str[i] == pattern[j] || pattern[j] == '.')
                return matchTwo(str, i + 1, length1, pattern, j + 1, length2);
            else {
                return false;
            }
        }
        if ((str[i] == pattern[j] || pattern[j] == '.') && pattern[j + 1] != '*')
            return matchTwo(str, i + 1, length1, pattern, j + 1, length2);
        if ((str[i] == pattern[j] || pattern[j] == '.') && pattern[j + 1] == '*')
            return matchTwo(str, i, length1, pattern, j + 2, length2)
                    || matchTwo(str, i + 1, length1, pattern, j, length2);
        if (pattern[j + 1] == '*') return matchTwo(str, i, length1, pattern, j + 2, length2);
        return false;
    }
}
