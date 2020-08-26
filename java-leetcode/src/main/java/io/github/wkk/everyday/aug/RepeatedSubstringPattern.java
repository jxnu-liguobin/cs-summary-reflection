/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * 459. 重复的子字符串
 *
 * @author kongwiki@163.com
 * @since 2020/8/24下午9:14
 */
public class RepeatedSubstringPattern {
    public static boolean repeatedSubstringPattern(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }
}
