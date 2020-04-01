package io.github.dreamylost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 梦境迷离
 * @description 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一个字母只会出现在其中的一个片段。
 *              返回一个表示每个字符串片段的长度的列表。 分隔字符串使同种字符出现在一起
 * @time 2018年4月4日
 */
public class Leetcode_763 {

	public static void main(String[] args) {
		String S = "ababcbacadefegdehijhklij";
		System.out.println("字符串长度：" + S.length());
		List<Integer> list = new Leetcode_763().partitionLabels(S);
		String result = list.stream().map(x -> x.toString()).collect(Collectors.joining(",", "[", "]"));
		System.out.println("结果：" + result);
	}

	/**
	 * 
	 * @author 梦境迷离
	 * @description 首先记录了每个字符的最后位置，然后使用两个指针记录起始位置和结束位置。对于每个字符，检查它当前位置是否是最后一个位置，
	 *              如果是，检查这个位置是否为结束。如果是的我们有分区。如果当前位置不是它最后出现的位置，则如果它大于结尾，则更新结尾。
	 * 
	 * @time 2018年4月4日
	 */
	public List<Integer> partitionLabels(String S) {
		List<Integer> ret = new ArrayList<>();
		int[] lastIdxs = new int[26];
		System.out.println("数组初始化为:a[i]为i的最后一次出现的位置");
		for (int i = 0; i < S.length(); i++) {
			lastIdxs[S.charAt(i) - 'a'] = i;
		}
		Arrays.stream(lastIdxs).forEach(x -> System.out.print("i" + x + " "));
		System.out.println();
		int startIdx = 0;
		while (startIdx < S.length()) {
			int endIdx = startIdx;
			// 检查它当前位置是否是最后一个位置,其中lastIdxs数组保存了字符a的最后一次出现的位置
			for (int i = startIdx; i < S.length() && i <= endIdx; i++) {
				int lastIdx = lastIdxs[S.charAt(i) - 'a'];
				if (lastIdx == i)
					continue;
				// 更新结束位置
				if (lastIdx > endIdx)
					endIdx = lastIdx;
			}
			// 计算长度
			ret.add(endIdx - startIdx + 1);
			startIdx = endIdx + 1;
		}
		return ret;
	}
}
