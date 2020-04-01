package io.github.dreamylost;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 统计二进制字符串中连续 1 和连续 0 数量相同的子字符串个数
 * 
 * 696. Count Binary Substrings (Easy)
 * 
 * Input: "00110011" Output: 6 Explanation: There are 6 substrings that have
 * equal number of consecutive 1's and 0's: "0011", "01", "1100", "10", "0011",
 * and "01"
 * 
 * @author 梦境迷离.
 * @time 2018年7月9日
 * @version v1.0
 */
public class Leetcode_696_String {

	public static void main(String[] args) {
		String s1 = "000011111111000000011111111000";
		int ret1 = 0;
		int ret2 = 0;
		ret1 = Leetcode_696_String.countBinarySubstrings(s1);
		ret2 = Leetcode_696_String.countBinarySubstrings2(s1);
		System.out.println(ret1);
		System.out.println(ret2);

	}

	/**
	 * 逻辑：每当我们在‘0’字符或‘1’字符之间切换时，我们都知道这是需要更新总数的时候。
	 * 如果我们有如下顺序.。000011111111000(4个零，9个1，3个零)总数是4+3。
	 * 为什么?因为我们基本上按如下方式对子字符串进行分组。(00001111)111000和00001111(111000)。
	 * 这里请注意，零是限制因素。如果我们有更多的零，我们可以有更多的子串，但既然我们没有，我们就不能再做了。一般来说，我们被较小的序列所包围。
	 * 
	 * @param s
	 * @return
	 *
	 */
	public static int countBinarySubstrings(String s) {
		int preLen = 0, curLen = 1, count = 0;
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == s.charAt(i - 1)) {
				curLen++;
			} else {
				preLen = curLen;
				curLen = 1;
			}

			if (preLen >= curLen) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 分析 可以发现如果是0011的话会包含2个，即0011 01 ；如果是000111的话包含3个，即000111 0011 01 ；01的话只有一个。
	 * 也就是说有多少个重复的01即有多少个情况， 拿实例1来说 00110011 ： 可以找出三组 0011 1100 00011，每组都有2个，也就是6个。
	 * 所以我们可以把字符串相邻的0,1个数统计出来 ，实例一可统计为： 2 2 2 ；相邻取比较小的那个值相加 为 2+2+2 = 6 再来一个例子
	 * 000110000111 ： 统计每块儿的个数： 3 2 4 3 ；相邻较小数字相加：2+2+3 = 7
	 * https://blog.csdn.net/qq_38595487/article/details/80280567
	 * 
	 * @param s
	 * @return
	 *
	 */
	public static int countBinarySubstrings2(String s) {

		int result = 0;
		List<Integer> list = new ArrayList<>();
		int index = 0;
		char temp = s.charAt(0);
		list.add(1);
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == temp) {
				list.set(index, list.get(index) + 1);
			} else {
				list.add(1);
				index++;
				temp = s.charAt(i);
			}
		}

		for (int i = 0; i < list.size() - 1; i++) {
			result += Math.min(list.get(i), list.get(i + 1));
		}

		return result;
	}

}
