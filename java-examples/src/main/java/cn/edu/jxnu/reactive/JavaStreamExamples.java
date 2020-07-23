/* All Contributors (C) 2020 */
package cn.edu.jxnu.reactive;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java流式编程的实例
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2019-12-07
 */
public class JavaStreamExamples {

    static final List<Integer> numbers = Arrays.asList(1, 2, 3);
    static final List<Integer> numbersPlusOne =
            numbers.stream().map(number -> number + 1).collect(Collectors.toList());

    public static void main(String[] args) {
        numbersPlusOne.forEach(System.out::println);
    }
}
