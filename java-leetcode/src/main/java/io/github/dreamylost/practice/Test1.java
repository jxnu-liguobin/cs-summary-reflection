/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice; // package cn.edu.jxnu.practice;
//
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Scanner;
// import java.util.TreeSet;
//
/// ** 超时
// * @author 梦境迷离.
// * @time 2018年8月11日
// * @version v1.0
// */
// public class Test1 {
//
//	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		int na = scanner.nextInt();
//		int mz = scanner.nextInt();
//		int k = scanner.nextInt();
//		char[] c1 = new char[na];
//		char[] c2 = new char[mz];
//
//		Arrays.fill(c1, 'a');
//		Arrays.fill(c2, 'z');
//		String string1 = new String(c1);
//		String string2 = new String(c2);
//		ArrayList<String> strings = new Test1().permutation(string1 + string2);
//		TreeSet<String> set = new TreeSet<>(strings);
//		int i = 0;
//		for (String s : set) {
//			i++;
//			if (i == k) {
//				System.out.println(s);
//				break;
//			} else if (i > k || k < 0 || k > strings.size()) {//条件判断应该提前。。。
//				System.out.println("-1");
//				break;
//			}
//		}
//		scanner.close();
//	}
//
//	public ArrayList<String> permutation(String str) {
//		ArrayList<String> res = new ArrayList<String>();
//		if (str != null && str.length() > 0) {
//			PermutationUtils2(str.toCharArray(), 0, res);
//		}
//		return res;
//	}
//
//	private void swapChar(char[] cs, int i, int j) {
//		char temp = cs[i];
//		cs[i] = cs[j];
//		cs[j] = temp;
//	}
//
//	public void PermutationUtils2(char[] str, int i, ArrayList<String> list) {
//		if (i == str.length - 1) { // 解空间的一个叶节点
//			list.add(String.valueOf(str)); // 找到一个解
//		}
//		for (int j = i; j < str.length; ++j) {
//			if (j == i || str[j] != str[i]) {
//				swapChar(str, i, j);
//				PermutationUtils2(str, i + 1, list);
//				swapChar(str, i, j); // 复位
//			}
//		}
//	}
//
// }
