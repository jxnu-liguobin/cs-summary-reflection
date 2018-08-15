package cn.edu.jxnu.leetcode;

import java.util.HashMap;
import java.util.Objects;

/**
 * 参考leetcode205
 * 
 * 290. Word Pattern 问题描述： Given a pattern and a string str, find if str follows
 * the same pattern.
 * 
 * Here follow means a full match, such that there is a bijection between a
 * letter in pattern and a non-empty word in str.
 * 
 * Examples:
 * 
 * pattern = "abba", str = "dog cat cat dog" should return true.
 * 
 * pattern = "abba", str = "dog cat cat fish" should return false.
 * 
 * pattern = "aaaa", str = "dog cat cat dog" should return false.
 * 
 * pattern = "abba", str = "dog dog dog dog" should return false.
 * 
 * Notes: You may assume pattern contains only lowercase letters, and str
 * contains lowercase letters separated by a single space.
 * 
 * @author 梦境迷离.
 * @time 2018年7月6日
 * @version v1.0
 */
public class Leetcode_290_String {

	// 同leetcode205
	public static boolean wordPattern_My(String pattern, String str) {
		if (pattern == null || str == null)
			return true;
		String[] strs = str.split(" ");
		if (pattern.length() != strs.length)
			return false;
		HashMap<Character, String> map = new HashMap<Character, String>();
		for (int i = 0; i < pattern.length(); i++) {
			if (map.containsKey(pattern.charAt(i))) {
				if (map.get(pattern.charAt(i)).equals(strs[i]))
					continue;
				else
					return false;
			} else {
				if (map.containsValue(strs[i]))
					return false;
				else
					map.put(pattern.charAt(i), strs[i]);
			}
		}
		return true;
	}

	// 网友的解析
	public static boolean wordPattern(String pattern, String str) {
		if (pattern == null || str == null)
			return true;
		String[] strs = str.split(" ");
		if (pattern.length() != strs.length)
			return false;
		HashMap<Object, Integer> map = new HashMap<Object, Integer>();
		for (int i = 0; i < pattern.length(); i++) {
			// abba----dog 
			// 新值，返回值	dog dog dog
			// a-0 -> null 	dog-0 ->null
			// b-1 -> null	dog-1 ->0
			// b-2 -> 1 	dog-2 ->1
			// a-3 -> 0		dog-3 ->2
			/**
			 * Map的put方法：
			 * 
			 * 1.要是之前没有关于该键的映射，在存储该键值对，然后然会null
			 * 
			 * 2.要是之前已经有关于该键的映射，则使用新的值替换旧值，并返回旧值。
			 * 
			 * 注意：返回null,也有可能是原来存在该键到null的映射，所以当更新值得时候 返回了以前的旧值Null
			 * 
			 * 
			 */
			if (!Objects.equals(map.put(pattern.charAt(i), i), map.put(strs[i], i))) {
				return false;
			}
		}
		return true;
	}

}
