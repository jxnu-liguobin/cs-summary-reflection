package cn.edu.jxnu.leetcode.dp;

/**
 * 376. 摆动序列
 * 
 * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
 * 
 * 例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和
 * [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
 * 
 * 给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。
 * 
 * 示例:
 * 
 * 输入: [1,7,4,9,2,5] 输出: 6 解释: 整个序列就是一个摆动序列。
 * 
 * 输入: [1,17,5,10,13,15,10,5,16,8] 输出: 7 解释:
 * 它的几个子序列满足摆动序列。其中一个是[1,17,10,13,10,16,8]。
 * 
 * 输入: [1,2,3,4,5,6,7,8,9] 输出: 2 进阶: 你能否用 O(n) 时间复杂度完成此题?
 * 
 * @author 梦境迷离.
 * @time 2018年6月10日
 * @version v1.0
 */
public class Leetcode_376_Dp {

	/**
	 * @param args
	 *
	 */
	public static void main(String[] args) {

		int[] arr = { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 };
		int i = Leetcode_376_Dp.wiggleMaxLength2(arr);
		System.out.println(i);

	}

	/**
	 * 仅使用2个临时变量
	 */
	public static int wiggleMaxLength(int[] nums) {
		int len = nums.length;
		if (len == 0)
			return 0;
		int up = 1, down = 1;
		for (int i = 1; i < len; i++) {
			if (nums[i] > nums[i - 1])
				up = down + 1;
			else if (nums[i] < nums[i - 1])
				down = up + 1;
		}

		return Math.max(up, down);
	}

	/**
	 * 使用临时dp数组
	 */
	public static int wiggleMaxLength2(int[] nums) {
		if (nums.length < 2)
			return nums.length;
		int n = nums.length;
		int[] dp = new int[n];
		dp[0] = 1;
		dp[1] = (nums[0] == nums[1]) ? 1 : 2;
		int maxCount = Math.max(dp[0], dp[1]);
		int flag = getFlag(nums[1], nums[0]);// -1
		// int[] arr = { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 };
		for (int i = 2; i < n; i++) {
			int f = getFlag(nums[i], nums[i - 1]);// 1 //向右重新计算
			if (f == 0 || f == flag) {
				dp[i] = dp[i - 1]; // 仍旧为前一个结果
				f = flag; // 标记为也相同
			} else {
				dp[i] = dp[i - 1] + 1; // dp[i]结果加1
			}
			// dp中最大的就是答案
			maxCount = Math.max(maxCount, dp[i]);
			flag = f; // 左边flag更新为右边的值
		}

		return maxCount;
	}

	/**
	 * 相邻元素相等返回0，或者左右标记位相同返回0
	 */
	private static int getFlag(int first, int second) {
		if (first > second)
			return 1;
		if (first < second)
			return -1;
		return 0;
	}

}
