package cn.edu.jxnu.practice;

/**
 * @description 在一个字符串(1<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置
 * @author Mr.Li
 * 
 */
public class LinkedHashMapDemo1 {

	public static void main(String[] args) {
		String str = "aabcd";
		int s = new LinkedHashMapDemo1().FirstNotRepeatingChar(str);
		System.out.println(s);
	}

	public int FirstNotRepeatingChar(String str) {
		if (str == null || str.equals(""))
			return -1;
		java.util.Map<Character, Integer> sMap = new java.util.LinkedHashMap<>();
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			// 集合不存在，记为1
			if (!sMap.containsKey(c[i])) {
				sMap.put(c[i], 1);
			} else {
				// 集合存在，+1
				sMap.put(c[i], sMap.get(c[i]) + 1);
			}
		}
		char temp = 0;
		for (char d : sMap.keySet()) {
			if (sMap.get(d).equals(1)) {
				temp = d;
				break;
			}
		}
		int result = 0;
		for (int i = 0; i < c.length; i++) {
			if (c[i] != temp) {
				result++;
			} else {
				break;
			}
		}
		return result;
	}
}