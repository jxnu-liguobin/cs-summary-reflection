/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @ClassName: StringDemo.java
 *
 * @author Mr.Li @Description: 请实现一个函数，将一个字符串中的空格替换成“%20”。例如，当字符串为We Are Happy.
 *     则经过替换之后的字符串为We%20Are%20Happy。 @Date 2018-1-21 下午7:42:07
 * @version V1.0
 */
public class ReplaceSpace {

    /** @param args */
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer("We Are Happy.");
        String s = ReplaceSpace.replaceSpace(stringBuffer);
        System.out.println(s);
    }

    public static String replaceSpace(StringBuffer str) {
        /*
         * StringBuilder sBuilder = new StringBuilder(); for (int i = 0; i <
         * str.length(); i++) { if (str.charAt(i) != ' ') {
         * sBuilder.append(str.charAt(i)); } else { sBuilder.append("%20"); } }
         * return sBuilder.toString();
         */
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                str.insert(i, "%20");
            }
        }
        return str.toString();
    }
}
