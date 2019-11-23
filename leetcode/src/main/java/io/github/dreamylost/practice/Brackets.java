package io.github.dreamylost.practice;

import java.util.Scanner;

/**
 * @description 括号匹配
 * @time 2018年4月9日
 */
public class Brackets {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int t = scanner.nextInt();
		for (int i = 0; i < t; i++) {
			String string = scanner.next();
			System.out.println(solve(string));
		}
		scanner.close();
	}

	public static String solve(String string) {
		int ls = string.length();
		int ans = 0;
		int left = 0;
		int right = 0;
		for (int i = 0; i < ls; i++) {
			if (string.charAt(i) == '(') {
				left += 1;
			}
			if (string.charAt(i) == ')') {
				right += 1;
			}
			ans = Math.max(ans, right - left);
		} // ()(),1-1,ans=0|2-2,ans=0
		if (left == right && ans <= 1 && left >= 2) {// ()，left=1
			return "Yes";
		}
		return "NO";
	}
}