/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.lambda;

/** 在Object中实现的方法都不能视为抽象方法 */
@FunctionalInterface
public interface NonFunc {
    boolean equals(Object object); // 不是抽象

    void handle(int i);
}
