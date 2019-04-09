package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 梦境迷离
 * @description 给定一个只包含数字的字符串，复原它并返回所有可能的IP地址格式。
 * 
 *              Given "25525511135",
 * 
 *              return ["255.255.11.135", "255.255.111.35"]. 我们可以不考虑数组内元素的顺序
 * @time 2018年4月9日
 */
public class Leetcode_93_DFS {

	public static void main(String[] args) {
		/**
		 * 这里有效IP有点歧义，00不能匹配成0，即长度大于1的时候，前导零是不允许的
		 */
		String ip = "25525511135";
		System.out.println(new Leetcode_93_DFS().restoreIpAddresses(ip));

	}

	public List<String> restoreIpAddresses(String s) {
		List<String> list = new ArrayList<String>();
		if (s.length() > 12)
			return list;
		helper(s, list, 0, "", 0);
		return list;
	}

	private void helper(String s, List<String> list, int pos, String res, int sec) {
		// 找到了四个section，res=255.255.11.135
		if (sec == 4 && pos == s.length()) {
			list.add(res);
			return;
		}
		// ip每个部分最大只有3位
		for (int i = 1; i <= 3; i++) {
			// 最大值是9+3=12 【因为ip是12位数字】
			if (pos + i > s.length()) {
				break;
			}
			// 临时section
			String section = s.substring(pos, pos + i);
			// 长度大于1，且开头为0，数值大于256的排除掉
			if (section.length() > 1 && section.startsWith("0") || Integer.parseInt(section) >= 256) {
				break;
			}
			helper(s, list, pos + i, sec == 0 ? section : res + "." + section, sec + 1);
		}
	}
}
