package cn.edu.jxnu.practice;

import java.util.Scanner;

/**
 * @description 小易有一个长度为N的正整数数列A = {A[1], A[2], A[3]..., A[N]}。 牛博士给小易出了一个难题:
 *              对数列A进行重新排列,使数列A满足所有的A[i] * A[i + 1](1 ≤ i ≤ N - 1)都是4的倍数。
 *              小易现在需要判断一个数列是否可以重排之后满足牛博士的要求。 
 *              输入描述: 输入的第一行为数列的个数t(1 ≤ t ≤ 10),
 *              接下来每两行描述一个数列A,第一行为数列长度n(1 ≤ n ≤ 10^5) 第二行为n个正整数A[i](1 ≤ A[i] ≤ 10^9)
 * 
 *              输出描述: 对于每个数列输出一行表示是否可以满足牛博士要求,如果可以输出Yes,否则输出No。
 * 
 *              输入例子1: 2 
 *              3
 *               1 10 100
 *              4 
 *               1 2 3 4
 * 
 *              输出例子1: Yes No
 * @author Mr.Li
 * 
 */
/*
 * 当时做的时候没有理清4跟2的关系，后来看了其他人用C写的,发现原来好简单
 * 主要思路：
 * 1如果数列中没有只能被2整除不能被4整除的数，就判断能被4整除的数量是否大于等于奇数-1
 * 
 * 2如果数列中有只能被2整除不能被4整除的数，就判断能被4整除的数量是否大于等于奇数
 */
public class Main4 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numCount = Integer.parseInt(sc.nextLine());
		int[][] numArrs = new int[numCount][];
		for (int i = 0; i < numCount; i++) {
			int l = Integer.parseInt(sc.nextLine());
			numArrs[i] = new int[l];
			String str = sc.nextLine();
			String[] strArr = str.split(" ");
			for (int j = 0; j < strArr.length; j++) {
				numArrs[i][j] = Integer.parseInt(strArr[j]);
			}
		}
		for (int k = 0; k < numArrs.length; k++) {
			if (new Main4().isRight(numArrs[k])) {
				System.out.println("Yes");
			} else
				System.out.println("No");
		}
			sc.close();
	}

	public boolean isRight(int[] numArr) {
		int countFour = 0;
		int countTwo = 0;
		int countOne = 0;
		//比如 1，100,10，其中含有2的个数大于等于2个的只有1个，等于1个的只有1个，
		//等于0个的也只有1个,当count2大于等于count0的时候一定就可以组成让相邻的数乘积是4的倍数。
		for (int i = 0; i < numArr.length; i++) {
			if (numArr[i] % 4 == 0) {
				countFour++;
			} else if (numArr[i] % 2 == 0) {
				countTwo++;
			} else {
				countOne++;
			}
		}
		 //1如果数列中没有只能被2整除不能被4整除的数，就判断能被4整除的数量是否大于等于奇数-1
		if (countTwo == 0) {
			if (countFour >= countOne - 1) {
				return true;
			} else {
				return false;
			}
			//2如果数列中有只能被2整除不能被4整除的数，就判断能被4整除的数量是否大于等于奇数
		} else {
			if (countFour >= countOne) {
				return true;
			} else {
				return false;
			}
		}
	}
}