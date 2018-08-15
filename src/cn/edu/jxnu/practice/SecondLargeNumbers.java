package cn.edu.jxnu.practice;

import java.util.Scanner;

/**
 * 
 * 1、第二大的数 【题目描述】输入 n 个整数，查找数组中第二大的数 
 * 输入描述: 第一行 n 表示 n 个数，第二行 n 个空格隔开的数 
 * 输出描述:
 * 输出第二大的数 输入例子 1: 
 * 5
 * 1 2 3 4 5 
 * 输出例子 1: 
 * 4
 * 
 * @author 梦境迷离.
 * @time 2018年8月3日
 * @version v1.0
 */
public class SecondLargeNumbers {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int max = Integer.MIN_VALUE;
		int sec = Integer.MIN_VALUE;
		int n = sc.nextInt();
		while (n-- > 0) {
			int temp = sc.nextInt();
			if (temp >= max) {
				sec = max; // sec=min,max=1 sec=1,max=2,sec=2,max=3...
				max = temp;
			} else if (temp >= sec) {
				sec = temp;
			}
		}
		System.out.print(sec);
		sc.close();
	}
}
