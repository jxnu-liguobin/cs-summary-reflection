package cn.edu.jxnu.reflect;

/**
 * 至多两个变量的整数或布尔表达式。
 * 
 * @author Eric Bruneton
 */
public interface Expression {

	/**
	 * Evaluates this expression.
	 * 
	 * @param i
	 *            the value of the first variable.
	 * @param j
	 *            the value of the second variable.
	 * @return the value of this expression for the given variable values.
	 */
	int eval(int i, int j);
}
