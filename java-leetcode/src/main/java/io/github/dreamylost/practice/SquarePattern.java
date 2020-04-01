package io.github.dreamylost.practice;

/**
 * @description 给定一个整数n，以下列方式打印n行。 如果n=4，生成的阵列将为 
 * 1*2*3*4 
 * 9*10*11*12 
 * 13*14*15*16
 * 5*6*7*8 
 * SquarePattern类的构造方法为squarePatternPrint。
 * 该函数的输入是一个整数n(0<=n<=100)。 不要从该方法返回任何内容。
 * 使用System out.print()或Systemout.println()打印所需的阵 列。 
 * 各输出行只能由“数字”和“*”组成。不应有空格。确保你的类和方法是公共的(public)。不要接受来自控制台的任何输入。
 * 应将其作为参 数传递到方法自身。 有用的命令：System.out.print()可将括号内的内容打印到屏幕上。
 * @author Mr.Li
 *
 */
public class SquarePattern {
	private int n;

	public SquarePattern(int n) {
		this.n = n;
	}

	public static void main(String args[]) {
		SquarePattern s = new SquarePattern(4);
		s.squarePatternPrint();
	}

	public void squarePatternPrint() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == 1) {
					if (j != n) {
						System.out.print(j + "*");
					} else {
						System.out.print(n);
						System.out.println();
					}
				} else if (i == n) {
					if (j != n) {
						System.out.print(n + j + "*");
					} else {
						System.out.print(2 * n);
						System.out.println();
					}
				}
				else {

					if (j != n) {
						System.out.print(i * n + j + "*");
					} else {
						System.out.print((i + 1) * n);
						System.out.println();
					}
				}
			}
		}
	}
}