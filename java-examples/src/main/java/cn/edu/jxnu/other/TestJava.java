/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.other;

/**
 * @author 梦境迷离
 * @time 2018-09-08
 */
public class TestJava {

    public static void main(String[] args) {
        String a = "A";
        String b = "B";
        StringBuffer sa = new StringBuffer();
        sa.append(a);
        change(sa, b);
        System.out.println(sa.toString()); // AB
    }

    public static void change(StringBuffer sa, String b) {
        sa.append(b);
    }
}
