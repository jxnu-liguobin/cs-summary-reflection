package cn.edu.jxnu.leetcode;
/**
 * 
	Backtracking
	回溯属于 DF，它不是用在遍历图的节点上，而是用于求解 排列组合 问题，例如有 { 'a','b','c' } 三个字符，求解所有由这三个字符排列得到的字符串。
	在程序实现时，回溯需要注意对元素进行标记的问题。使用递归实现的回溯，在访问一个新元素进入新的递归调用时，需要将新元素标记为已经访问，
	这样才能在继续递归调用时不用重复访问该元素；但是在递归返回时，需要将该元素标记为未访问，因为只需要保证在一个递归链中不同时访问一个元素，
	可以访问已经访问过但是不在当前递归链中的元素。
	数字键盘组合【1，2，3，4，5，6，7，8，9，0】
	
	Input:Digit string "23"
	Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
Leetcode : 17. Letter Combinations of a Phone Number (Medium)
 * @author 梦境迷离
 * @version V1.0
 * @time. 2018年4月12日
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_17_Backtracking {
	// 所有数字对应的所有字符，keys[3]对应数字3的字符表示
	private static final String[] KEYS = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

	public List<String> letterCombinations(String digits) {
		List<String> ret = new ArrayList<String>();
		if (digits == null || digits.length() == 0)
			return ret;
		combination(new StringBuilder(), digits, ret);
		return ret;
	}

	/**
	 * 使用递归
	 * @time.	下午9:11:35
	 * @version V1.0
	 * @param prefix
	 * @param digits
	 * @param ret
	 */
	private void combination(StringBuilder prefix, String digits, List<String> ret) {
		if (prefix.length() == digits.length()) {
			ret.add(prefix.toString());
		}
		// 得到数字对应的字母表示字符串，字符串2->letters='abc'
		// 3->letters def.[先是ab,ac,ad,ae,af......,删除第二个元素，进行第二个字符2对应的def的循环，进行同样的操作]
		String letters = KEYS[(digits.charAt(prefix.length()) - '0')];
		for (char c : letters.toCharArray()) {
			prefix.append(c);
			combination(prefix, digits, ret);
			prefix.deleteCharAt(prefix.length() - 1);// 删除
		}
	}

	/**
	 * 使用队列
	 *
	 * @time. 下午9:02:35
	 * @version V1.0
	 * @param digits
	 * @return
	 */
	public List<String> letterCombinations2(String digits) {
		LinkedList<String> ans = new LinkedList<String>();
		if (digits.isEmpty())
			return ans;
		String[] mapping = new String[] { "0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
		ans.add("");
		for (int i = 0; i < digits.length(); i++) {
			//得到Unicode数值
			int x = Character.getNumericValue(digits.charAt(i));
			while (ans.peek().length() == i) {
				String t = ans.remove();
				for (char s : mapping[x].toCharArray())
					ans.add(t + s);
			}
		}
		return ans;
	}

	/**
	 * 使用循环
	 * @time.	下午9:10:07
	 * @version V1.0
	 * @param digits
	 * @return
	 */
	public static List<String> letterCombinations3(String digits) {
		String digitletter[] = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
		List<String> result = new ArrayList<String>();

		if (digits.length() == 0)
			return result;
		result.add("");
		for (int i = 0; i < digits.length(); i++)
			result = combine(digitletter[digits.charAt(i) - '0'], result);
		return result;
	}

	public static List<String> combine(String digit, List<String> l) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < digit.length(); i++)
			for (String x : l)
				result.add(x + digit.charAt(i));
		return result;
	}
}
