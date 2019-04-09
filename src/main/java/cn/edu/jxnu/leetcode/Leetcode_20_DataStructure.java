package cn.edu.jxnu.leetcode;

import java.util.Stack;

/**
 * 
 * 用栈实现括号匹配
 * 
 * 20. Valid Parentheses (Easy)
 * 
 * "()[]{}"
 * 
 * Output : true
 * 
 * @author 梦境迷离.
 * @time 2018年7月2日
 * @version v1.0
 */
public class Leetcode_20_DataStructure {

	/**
	 * 使用栈
	 *
	 */
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<>();
		for (char c : s.toCharArray()) {
			if (c == '(' || c == '{' || c == '[') {
				stack.push(c);
			} else {
				if (stack.isEmpty()) {
					return false;
				}
				char cStack = stack.pop();// 先进后出，如果找到一个‘（’但是栈顶不为‘)’，则不可能匹配
				boolean b1 = c == ')' && cStack != '(';
				boolean b2 = c == ']' && cStack != '[';
				boolean b3 = c == '}' && cStack != '{';
				if (b1 || b2 || b3) {
					return false;
				}

			}
		}
		return stack.isEmpty();
	}

	public boolean isValid3(String s) {
		Stack<Character> stack = new Stack<Character>();
		for (char c : s.toCharArray()) {
			if (c == '(')
				stack.push(')');
			else if (c == '{')
				stack.push('}');
			else if (c == '[')
				stack.push(']');
			else if (stack.isEmpty() || stack.pop() != c)
				return false;
		}
		return stack.isEmpty();
	}

	/**
	 * 不用栈，使用数组
	 */
	public boolean isValid2(String s) {
		char[] input = s.toCharArray();
		char[] stack = new char[input.length];
		int i = -1;
		for (char c : input) {
			if (c == '(' || c == '{' || c == '[')
				stack[++i] = c;
			else if (c == ')' || c == '}' || c == ']') {
				if (i < 0)
					return false;
				else if (c == ')' && stack[i] == '(')
					i--;
				else if (c == '}' && stack[i] == '{')
					i--;
				else if (c == ']' && stack[i] == '[')
					i--;
				else
					i++;
			}
		}
		return i == -1;
	}

}
