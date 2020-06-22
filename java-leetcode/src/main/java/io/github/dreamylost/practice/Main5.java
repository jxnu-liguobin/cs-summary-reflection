/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * @description 思路：每次在原字符串中取出一个字符，将取出的字符在剩余的字符串中插空，剩余字符串保持相对位置不变，
 *     如果插孔后的字符串符合括号规则，而且与原字符串不相同，那么这个字符串就是长度与原字符串相同的
 *     一个符合条件的字符串结果，且能够保证LCS（S,T）最大。将符合条件的字符串存入一个map中，最后 统计map的size即可。
 * @author Mr.Li
 */
public class Main5 {
    /**
     * 例如"abcde"的子序列有"abe","","abcde"等。 定义LCS(S,T)为字符串S和字符串T最长公共子序列的长度,即一个最长的序列W既是S的子序列也是T的子序列的长度。
     * 小易给出一个合法的括号匹配序列s,小易希望你能找出具有以下特征的括号序列t: 1、t跟s不同,但是长度相同 2、t也是一个合法的括号匹配序列 3、LCS(s,
     * t)是满足上述两个条件的t中最大的 因为这样的t可能存在多个,小易需要你计算出满足条件的t有多少个。 如样例所示: s ="(())()",跟字符串s长度相同的合法括号匹配序列有:
     * "()(())", "((()))", "()()()", "(()())",其中LCS( "(())()", "()(())" )为4,其他三个都为5,所以输出3.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] c = s.toCharArray();
        int len = c.length;
        // HashMap<String, Integer> map = new HashMap<String Integer>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (int i = 0; i < len; i++) {
            // 留空
            String s1 = s.substring(0, i); // 每次循环截取字符串中的一个字符出来
            String s2 = s.substring(i + 1, len);
            String s3 = s1 + s2;
            // 插入
            for (int j = 0; j < len; j++) {
                String b1 = s3.substring(0, j);
                String b2 = s3.substring(j, len - 1);
                String e = String.valueOf(c[i]);
                String b3 = b1 + e + b2; // 将截取出来的字符依次在剩余字符串中插空

                if (b3.equals(s)) continue;
                else {
                    // 匹配
                    if (istrue(b3)) map.put(b3, 1);
                }
            }
        }
        System.out.println(map.size());
        // System.out.println(istrue("(())()"));
        sc.close();
    }

    public static boolean istrue(String s) {
        char[] c = s.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < c.length; i++) {
            if (stack.isEmpty()) {
                if (c[i] == '(') stack.push(c[i]);
                else {
                    return false;
                }
            } else {
                if (c[i] == ')') stack.pop();
                else stack.push(c[i]);
            }
        }
        if (stack.size() == 0) return true;
        else return false;
    }
}
