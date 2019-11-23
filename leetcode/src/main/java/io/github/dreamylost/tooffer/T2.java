package io.github.dreamylost.tooffer;

/**
 * 请实现一个函数，将一个字符串中的空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 */
public class T2 {

    public static String replaceSpace1(StringBuffer str) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = str.toString().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                stringBuffer.append("%20");
            } else {
                stringBuffer.append(chars[i]);
            }
        }
        return stringBuffer.toString();
    }

    public static String replaceSpace2(StringBuffer str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                stringBuffer.append("%20");
            } else {
                stringBuffer.append(str.charAt(i));
            }
        }
        return stringBuffer.toString();
    }

    public static String replaceSpace3(StringBuffer str) {
        return str.toString().replaceAll("\\s", "%20");
    }

    public static void main(String[] args) {
        System.out.println(replaceSpace3(new StringBuffer("We Are Happy")));
    }

}
