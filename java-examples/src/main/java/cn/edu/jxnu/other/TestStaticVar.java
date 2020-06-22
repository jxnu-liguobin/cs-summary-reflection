/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.other;

/**
 * @author 梦境迷离
 * @description
 * @time 2018年3月28日
 */
public class TestStaticVar {

    @SuppressWarnings("unused")
    private static int count = 10;

    public static void main(String[] args) {
        // 笔试题，优先使用局部
        int count = 0;
        System.out.println(count);
        // 笔试题，Date不是lang包下的，因为需要导包
    }
}
