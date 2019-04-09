package cn.edu.jxnu.leetcode;

/**
 * 	素数分解
	
	每一个数都可以分解成素数的乘积，例如 84 = 22 * 31 * 50 * 71 * 110 * 130 * 170 * …
	
	整除[mi在右上角是幂次]
	令 x = 2m0 * 3m1 * 5m2 * 7m3 * 11m4 * …
	令 y = 2n0 * 3n1 * 5n2 * 7n3 * 11n4 * …
	如果 x 整除 y（y mod x == 0），则对于所有 i，mi <= ni。
	最大公约数最小公倍数
	x 和 y 的最大公约数为：gcd(x,y) = 2min(m0,n0) * 3min(m1,n1) * 5min(m2,n2) * ...
	x 和 y 的最小公倍数为：lcm(x,y) = 2max(m0,n0) * 3max(m1,n1) * 5max(m2,n2) * ...
	
	生成素数序列
	204. Count Primes (Easy)
	埃拉托斯特尼筛法在每次找到一个素数时，将能被素数整除的数排除掉。
 * @author 梦境迷离.
 * @time 2018年6月22日
 * @version v1.0
 */
public class Leetcode_204_Math {

	public static void main(String[] args) {
		int n = 10;
		int ret = Leetcode_204_Math.countPrimes(n);
		System.out.println(ret);
	}

	public static int countPrimes(int n) {
		boolean[] notPrimes = new boolean[n + 1];
		int count = 0;
		// 素数从2~n
		for (int i = 2; i < n; i++) {
			if (notPrimes[i]) {// 已经找到，跳过
				continue;
			}
			count++;// 计数
			// 从 i * i 开始，因为如果 k < i，那么 k * i 在之前就已经被去除过了
			for (long j = (long) (i) * i; j < n; j += i) {
				notPrimes[(int) j] = true;
			}
		}
		return count;
	}

}
