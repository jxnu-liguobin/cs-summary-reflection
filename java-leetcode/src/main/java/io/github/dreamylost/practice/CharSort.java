package io.github.dreamylost.practice;

import java.util.Scanner;

/**
 * 1、字符排序 【题目描述】输入一串字符，包含数字[0-9]和小写字母[a-z]，要求按数字从小到大、字母从 a 到 z 排序， 并且所有数字排在字母后面
 * 
 * @author 梦境迷离.
 * @time 2018年8月2日
 * @version v1.0
 */
public class CharSort {

	/**
	 * 变相的冒泡排序算法，判断条件追加字母与数字的区分
	 * 
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String arg = scanner.nextLine();
		char[] str = arg.toCharArray();
		for (int i = 0; i < str.length - 1; i++) {
			for (int j = 0; j < str.length - 1 - i; j++) {
				// 先使得字母前移，再使得小字母/小数字前移
				if ((str[j] <= '9' && str[j + 1] >= 'a') || (str[j] > str[j + 1] && str[j] <= '9')
						|| (str[j] > str[j + 1] && str[j + 1] >= 'a')) {
					char temp = str[j];
					str[j] = str[j + 1];
					str[j + 1] = temp;
				}
			}
		}
		System.out.println(new String(str));
		scanner.close();
	}

}
