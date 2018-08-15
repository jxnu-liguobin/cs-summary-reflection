package cn.edu.jxnu.other;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * @description 模拟掷骰子
 *
 */
public class RandMath {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String t = null;
		System.out.println("请输入需要模拟的次数：");
		t = scanner.next();
		while (!"exit".equals(t)) {
			System.out.println("请输入需要模拟的次数：");
			// 第一次以后使用
			if (t == null)
				t = scanner.next();
			int j = 0;
			if ("exit".equals(t))
				return;
			if (t != null) {
				try {
					j = Integer.parseInt(t);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("您输入的次数有误！");
				} finally {
					// 转化失败 exit 退出
					if (j == 0 || "exit".equals(t)) {
						scanner.close();
						System.exit(-1);
					}
				}
			}
			// 以当前系统计时器的当前值作为种子，以纳秒为单位。
			Random random = new Random(System.nanoTime());
			// 保存点数的次数
			Map<String, Integer> res = new HashMap<String, Integer>();
			int curr = 0;
			int temp = 0;
			for (int i = 0; i < j; i++) {
				// 产生1~6
				curr = random.nextInt(6) + 1;
				if (!res.containsKey(String.valueOf(curr))) {
					temp = 1;
				} else {
					temp = res.get(String.valueOf(curr)).intValue() + 1;
				}
				res.put(String.valueOf(curr), Integer.valueOf(temp));
			}
			double gl6 = 0.0;
			if (res.get("6") != null) {
				gl6 = (double) res.get("6") / j;
			}
			System.out.println("1的次数：" + res.get("1"));
			System.out.println("2的次数：" + res.get("2"));
			System.out.println("3的次数：" + res.get("3"));
			System.out.println("4的次数：" + res.get("4"));
			System.out.println("5的次数：" + res.get("5"));
			System.out.println("6的次数：" + res.get("6") + "\t概率：" + gl6);
			t = null;
		}
	}
}
