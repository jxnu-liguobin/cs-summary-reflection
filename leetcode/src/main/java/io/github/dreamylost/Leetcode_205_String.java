package io.github.dreamylost;

import java.util.HashMap;

/**
 * 
 * 字符串同构
 * 
 * 205. Isomorphic Strings (Easy)
 * 
 * Given "egg", "add", return true. Given "foo", "bar", return false. Given
 * "paper", "title", return true. 给定两个字符串s and t，判断它们是否是同构字符串。 同构字符串是指，s
 * 中的字符可以被替换以得到 t。 字符串中同一个字符的所有出现位置必须被相同的字符替换，并且不改变其在原始字符串中的位置。
 * 不同的字符不能够被映射到相同的字符，但是一个字符可以映射到本身,例：
 * 
 * @author 梦境迷离.
 * @time 2018年7月6日
 * @version v1.0
 */
public class Leetcode_205_String {

	public static void main(String[] args) {
		String s1 = "egg";
		String s2 = "add";
		boolean f = Leetcode_205_String.isIsomorphic(s1, s2);
		System.out.println(f);

	}

	/**
	 * 使用map集合
	 * 
	 * 若s与t不等，直接返回false?
	 */
	public static boolean isIsomorphic2(String s, String t) {
		if (s == null || s.length() <= 1)
			return true;
		HashMap<Character, Character> hm = new HashMap<Character, Character>();
		for (int i = 0; i < s.length(); i++) {
			char c1 = s.charAt(i);
			char c2 = t.charAt(i);
			if (hm.containsKey(c1)) {
				// 若c1在集合中，判断c2和当前c2是否是相等
				if (hm.get(c1) == c2)
					continue;// 字符可以被字符自己替换
				else
					return false;
			} else {
				// c2在集合中
				if (hm.containsValue(c2))
					return false;
				else {
					// 既没有c1,也没有c2，直接添加hash映射c1->c2
					hm.put(c1, c2);
				}
			}
		}
		return true;
	}

	/**
	 * 记录一个字符上次出现的位置，如果两个字符串中的字符上次出现的位置一样，那么就属于同构。
	 * 
	 * 字符只能是256个，否则不可行
	 */
	public static boolean isIsomorphic(String s, String t) {
		int[] preIndexOfS = new int[256];
		int[] preIndexOfT = new int[256];
		for (int i = 0; i < s.length(); i++) {
			char sc = s.charAt(i), tc = t.charAt(i);
			if (preIndexOfS[sc] != preIndexOfT[tc]) {
				return false;
			}
			preIndexOfS[sc] = i + 1;
			preIndexOfT[tc] = i + 1;
		}
		return true;
	}

}
