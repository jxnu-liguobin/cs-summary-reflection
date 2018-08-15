package cn.edu.jxnu.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 查找字符串中重复的字符串，并将重复字符串及对应的重复次数打印出来：
 * @author Mr.Li
 *
 */
public class Main8 {

	public static void main(String[] args) {
		String[] strings = { "123", "123", "asd", "asdfg","asd" };
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < strings.length; i++) {
			if (!map.containsKey(strings[i])) {
				map.put(strings[i], 1);
			} else {
				map.put(strings[i], map.get(strings[i]) + 1);
			}
		}
		for (String string : map.keySet()) {
			if (map.get(string) > 1) {
				System.out.println("字符串：" + string + "次数：" + map.get(string));
			}
		}
	}

}
