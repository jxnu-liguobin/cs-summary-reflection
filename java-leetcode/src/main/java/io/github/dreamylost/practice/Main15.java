package io.github.dreamylost.practice;

import java.util.Scanner;

/**
 * @author 梦境迷离
 * @description 按照卡中心校园招聘的要求，HR小招和小商需要从三个科室中（分别为A、B、C）抽派面试官去往不同城市。
 *              两名HR按照以下规定轮流从任一科室选择面试官：每次至少选择一位，至多选择该科室剩余面试官数。最先选不到面试官的HR需要自己出差。
 *              假设HR小招和小商都不想出差且每次选择都采取最优策略，如果是小招先选，写一个函数来判断她是否需要出差。如果不需要出差，
 *              请给出第一步的最优策略。
 * @time 2018年3月29日
 */

public class Main15 {
	/**
	 * 异或和为0的则先手必败，所以A^B^C=0时就输出1，然后赢得情况， 因为只有三个数就很简单了，
	 * 枚举两两的异或和只有第三个的值大于这个异或值，就输出第三个值减去另外两个数的异或值就是答案了。
	 * 
	 * @author 优雅1217
	 * @description
	 * @time 2018年3月29日
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String inputString = sc.next().toString();
		String stringArray[] = inputString.split(",");
		int num[] = new int[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			num[i] = Integer.parseInt(stringArray[i]);
		}
		int a = num[0];
		int b = num[1];
		int c = num[2];
		int bool = a ^ b ^ c;
		if (bool == 0)
			System.out.print(1);
		else {
			if ((a ^ b) < c) {
				System.out.print("C," + (c - (a ^ b)));
			}
			if ((a ^ c) < b) {
				System.out.print("B," + (b - (a ^ c)));
			}
			if ((b ^ c) < a) {
				System.out.print("A," + (a - (b ^ c)));
			}
		}
		sc.close();
	}

}
