package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 241. Different Ways to Add Parentheses (Medium) 给表达式加括号
 * 
 * Input: "2-1-1".
 * 
 * ((2-1)-1) = 0
 * 
 * (2-(1-1)) = 2
 * 
 * Output : [0, 2]
 * 
 * @author 梦境迷离.
 * @time 2018年6月6日
 * @version v1.0
 */
public class Leetcode_241 {

	public static void main(String[] args) {
		String iString = "+2-1-1";
		List<Integer> list = new Leetcode_241().diffWaysToCompute(iString);
		System.err.println(list);
	}

	public List<Integer> diffWaysToCompute(String input) {

		List<Integer> ways = new ArrayList<>();// 保存了2
		if (input == null || input.length() == 0) {
			return ways;
		}

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			// 分治，以运算符分为左右两部分
			if (c == '+' || c == '-' || c == '*') {
				// 对左边进行同样的处理
				List<Integer> left = diffWaysToCompute(input.substring(0, i));
				// 对右边进行同样的处理
				List<Integer> right = diffWaysToCompute(input.substring(i + 1));
				for (int l : left) {
					for (int r : right) {
						switch (c) {
						case '+':
							ways.add(l + r);
							break;
						case '-':
							ways.add(l - r);
							break;
						case '*':
							ways.add(l * r);
							break;
						}
					}
				}
			}
		}

		// 添加
		if (ways.size() == 0) {
			if (input.charAt(0) == '+') {
				ways.add(Integer.valueOf(input.substring(1)));
			} else if (input.charAt(0) == '-') {
				ways.add(-1 * Integer.valueOf(input.substring(1)));
			} else {
				ways.add(Integer.valueOf(input));
			}
		}
		return ways;
	}
}
