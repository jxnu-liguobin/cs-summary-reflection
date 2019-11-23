package io.github.dreamylost.practice;

/**
 * <p>
 * Title: Main.java
 * </p>
 * <p>
 * Description:小易准备去魔法王国采购魔法神器,购买魔法神器需要使用魔法币,但是小易现在一枚魔法币都没有,
 * 但是小易有两台魔法机器可以通过投入x(x可以为0)个魔法币产生更多的魔法币。
 *  魔法机器1:如果投入x个魔法币,魔法机器会将其变为2x+1个魔法币
 *  魔法机器2:如果投入x个魔法币,魔法机器会将其变为2x+2个魔法币
 *  
 * 小易采购魔法神器总共需要n个魔法币,所以小易只能通过两台魔法机器产生恰好n个魔法币,
 * 小易需要你帮他设计一个投入方案使他最后恰好拥有n个魔法币。 输入描述:
 * 
 * 输入包括一行,包括一个正整数n(1 ≤ n ≤ 10^9),表示小易需要的魔法币数量。
 * 
 * 输出描述: 输出一个字符串,每个字符表示该次小易选取投入的魔法机器。其中只包含字符'1'和'2'。
 * 
 * 输入例子1: 10
 * 
 * 输出例子1: 122
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * School: jxnu
 * </p>
 * 
 * @author Mr.Li
 * @date 2018-2-16
 * @version 1.0
 */
public class Main0 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main0 sTest = new Main0();
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		int s = scanner.nextInt();
		String string = sTest.get(s);
		System.out.println(string);
		scanner.close();

	}

	/**
	 * @desciption
	 * @param n
	 * @return
	 */
	public String get(int n) {
		String res = "";
		while (n > 0) {
			if (n % 2 == 0) {
				res += '2' + ",";
				n = (n - 2) / 2;
			} else {
				res += '1' + ",";
				n = (n - 1) / 2;
			}
		}
		String string = "";
		String[] t = res.substring(0, res.length() - 1).split(",");
		for (int i = t.length - 1; i >= 0; i--) {
			string += t[i];
		}
		return string;

	}
}
