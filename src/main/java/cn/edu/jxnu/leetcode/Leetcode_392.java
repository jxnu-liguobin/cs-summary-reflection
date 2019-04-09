package cn.edu.jxnu.leetcode;

/**
 * @author 梦境迷离
 * @description 判断是否为子串 s = "abc", t = "ahbgdc" Return true.
 * @time 2018年4月1日
 */
public class Leetcode_392 {
	public boolean isSubsequence(String s, String t) {
		for (int i = 0, pos = 0; i < s.length(); i++, pos++) {
			// 从指定的索引处开始，返回第一次出现的指定子字符串在此字符串中的索引。
			pos = t.indexOf(s.charAt(i), pos);
			if (pos == -1)
				return false;
		}
		return true;
	}
}
