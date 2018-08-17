package cn.edu.jxnu.other;

import java.util.ArrayList;
import java.util.List;

public class Test {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		short s = 128;
		byte b = (byte) 128;
		System.out.println(s);
		System.out.println(b);
		String ss = "hello world";
		// 替换所有包含o的字符串
		String result = ss.replace("o", "");
		String result2 = ss.replaceAll("o", "");
		String result3 = ss.replaceFirst("o", "");
		System.out.println("replace:" + result + "\nreplaceAll:" + result2 + "\nreplaceFirst:" + result3);
		// 下列报错，点不可以赋给任何点
		// ArrayList<String> list = new ArrayList<>();
		// ArrayList<Object> list2 = list;
		// 下列正确
		// ArrayList<Object> list = new ArrayList<>();
		// List<?> sList = list;
		// 下列正确。点赋给范围是OK的
		List<? extends Object> list3 = new ArrayList<String>();
		// ? 无限制
		// <? extends> 不能存，即set方法无效
		// <super ?> 不能取，即get方法失效
	}
}
