package io.github.dreamylost.practice;

/**
 * @description 求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？
 *              为此他特别数了一下1~13中包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。
 *              ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数。
 * @author Mr.Li
 * 
 */
public class NumberOf1Between1AndN {
	public static void main(String[] args) {
//		long n = 99999999999L;
//		long start1 = System.currentTimeMillis();
//		System.out.println("start:" + start1);
//		long s = new NumberOf1Between1AndN().NumberOf1Between1AndN_Solution2(n);
//		long end1 = System.currentTimeMillis();
//		System.out.println("end:" + end1);
//		System.out.print("a number spand time :");
//		System.out.println(end1 - start1);
//		System.out.println("result:" + s);
//		System.out.print("may span total spand time :");
//		long c = 99999999999l - 1111111110l;
//		System.out.println(c * (System.currentTimeMillis() - start1) / 1000
		//		/ 60 / 60 + " hours");
		 long start = System.currentTimeMillis() / 1000000;
		 long r = new NumberOf1Between1AndN().isExistNumber();
		 System.out.println("result:" + r);
		 System.out.print("spand time :");
		 System.out.println(System.currentTimeMillis() / 1000000 - start);
	}

	/**
	 * @DESCRIPTION 编码之美 NlogN
	 * @param n
	 * @return
	 */
	public int NumberOf1Between1AndN_Solution(int n) {
		int count = 0;
		for (int i = 1; i <= n; i++) {
			count += Count1InAinteger(i);
		}
		return count;
	}

	private int Count1InAinteger(int n) {
		int num = 0;
		while (n != 0) {
			num += (n % 10 == 1) ? 1 : 0;
			n /= 10;
		}
		return num;
	}

	/**
	 * @description O(n) 求1~n中含有1的个数
	 * @param n
	 * @return
	 */
	public long NumberOf1Between1AndN_Solution2(long n) {
		long count = 0;
		long factor = 1;
		long lowerNum = 0;
		long currNum = 0;
		long highNum = 0;
		while (n / factor != 0) {
			lowerNum = n - (n / factor) * factor;
			currNum = (n / factor) % 10;
			highNum = n / (factor * 10);
			switch ((int) currNum) {
			case 0:
				count += highNum * factor;
				break;
			case 1:
				count += highNum * factor + lowerNum + 1;
				break;
			// 由高位决定
			default:
				count += (highNum + 1) * factor;
				break;
			}
			factor *= 10;
		}
		return count;
	}

	/**
	 * @description 求解满足上述函数f(n) = n 最大n的值 已经证 上界是10^11
	 */
	public long isExistNumber() {
		long N = 99999999999L;
		long n = 0L;
		NumberOf1Between1AndN solution = new NumberOf1Between1AndN();
		while (n < N) {
			System.out.print("cur n:" + n);
			long i = solution.NumberOf1Between1AndN_Solution2(n);
			System.out.println(" cur i:" + i);
			//求出一含有520个1的最小N
			if (i == 1314) {
				return n;
			}
			n++;
		}
		return -1l;
	}
}