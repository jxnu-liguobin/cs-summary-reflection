/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.lambda;

/** 动物 */
public interface IAnimal {
    default void breath() {
        System.out.println("breath");
    }
}
