package cn.edu.jxnu.lambda;

/**
 * 动物
 */
public interface IAnimal {
	default void breath() {
		System.out.println("breath");
	}
}
