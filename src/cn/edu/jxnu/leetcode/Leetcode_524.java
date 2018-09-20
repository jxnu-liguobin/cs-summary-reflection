package cn.edu.jxnu.leetcode;

import java.util.List;

/**
 * @author 梦境迷离
 * @description 最长子序列
 * 
 *              Input: s = "abpcplea", d = ["ale","apple","monkey","plea"]
 * 
 *              Output: "apple"
 * 
 *              题目描述：删除 s 中的一些字符，使得它构成字符串列表 d
 *              中的一个字符串，找出能构成的最长字符串。如果有多个相同长度的结果，返回按字典序排序的最大字符串。
 * @time 2018年4月7日
 */
public class Leetcode_524 {
	public String findLongestWord(String s, List<String> d) {
		String ret = "";
		for (String str : d) {
			for (int i = 0, j = 0; i < s.length() && j < str.length(); i++) {
				if (s.charAt(i) == str.charAt(j))
					j++;
				if (j == str.length()) {
					// 取长度最大的，且字典排序最大的
					if (ret.length() < str.length() || (ret.length() == str.length() && ret.compareTo(str) > 0)) {
						ret = str;
					}
				}
			}
		}
		return ret;
	}
}
