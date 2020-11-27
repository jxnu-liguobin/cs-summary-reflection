/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 定义排序用的常量,公共方法
 *
 * <p>这里最好使用泛型类，更通用
 *
 * @since 2020年11月27日
 */
public abstract class Constant<T extends Number> {

    public static final Integer[] array =
            new Integer[] {8, 34, 64, 51, 33, 22, 44, 55, 88, 1, 0, 2, 2};
    // 有序数组
    static final Integer[] array2 = new Integer[] {0, 1, 2, 2, 8, 22, 33, 34, 44, 51, 55, 64, 88};
    public static final int len = array.length;

    public final void printResult(T[] array) throws Exception {
        if (array == null || array.length == 0)
            throw new Exception("no element or invalid element in array");
        // java 8 lambda
        System.out.println(
                Arrays.stream(array)
                        .map(Object::toString)
                        .collect(Collectors.joining(",", "[", "]")));
    }

    /**
     * 原始版
     *
     * @param array
     * @param len
     * @return T[]
     */
    public abstract void sort(T[] array, int len) throws Exception;

    /**
     * 优化版
     *
     * @param array
     * @param len
     * @return T[]
     */
    public void sort2(T[] array, int len) throws Exception {}
}
