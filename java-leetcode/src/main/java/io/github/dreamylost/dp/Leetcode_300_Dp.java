/* All Contributors (C) 2020 */
package io.github.dreamylost.dp;

/**
 * 如果在子序列中，当下标 ix > iy 时，Six > Siy，称子序列为原序列的一个 递增子序列 。
 *
 * <p>定义一个数组 dp 存储最长递增子序列的长度，dp[n] 表示以 Sn 结尾的序列的最长递增子序列长度。对于一个递增子序列 {Si1, Si2,...,Sim}，如果 im < n 并且
 * Sim < Sn ，此时 {Si1, Si2,..., Sim, Sn} 为一个递增子序列，递增子序列的长度增加
 * 1。满足上述条件的递增子序列中，长度最长的那个递增子序列就是要找的，在长度最长的递增子序列上加上 Sn 就构成了以 Sn 为结尾的最长递增子序列。因此 dp[n] = max{ dp[i]+1
 * | Si < Sn && i < n} 。
 *
 * <p>因为在求 dp[n] 时可能无法找到一个满足条件的递增子序列，此时 {Sn} 就构成了递增子序列，需要对前面的求解方程做修改，令 dp[n] 最小为
 * 1，即：dp[n]=max{1,dp[i]+1 | si<sn && i<n }
 *
 * <p>对于一个长度为 N 的序列，最长递增子序列并不一定会以 SN 为结尾，因此 dp[N] 不是序列的最长递增子序列的长度，需要遍历 dp 数组找出最大值才是所要的结果，即 max{
 * dp[i] | 1 <= i <= N} 即为所求。
 *
 * @author 梦境迷离.
 * @time 2018年6月9日
 * @version v1.0
 */
public class Leetcode_300_Dp {

    /** @param args */
    public static void main(String[] args) {

        int[] array = {10, 9, 2, 5, 3, 7, 101, 18};
        int ret = Leetcode_300_Dp.lengthOfLIS2(array);
        System.out.println(ret);
    }

    /**
     * 解法的时间复杂度为 O(N2)
     *
     * @param nums
     * @return
     */
    public static int lengthOfLIS(int[] nums) {

        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            int max = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max = Math.max(max, dp[j] + 1);
                }
            }
            // 更新dp数组
            dp[i] = max;
        }
        // 速度慢
        // return Arrays.stream(dp).max().orElse(0);
        int ret = 0;
        for (int i = 0; i < n; i++) {
            ret = Math.max(ret, dp[i]);
        }

        return ret;
    }

    /**
     * 可以使用二分查找将时间复杂度降低为 O(NlogN)。 定义一个 tails 数组，其中 tails[i] 存储长度为 i + 1的最长递增子序列的最后一个元素。
     * 如果有多个长度相等的最长递增子序列，那么 tails[i] 就取最小值。例如对于数组 [4,5,6,3] 有
     *
     * <p>len =1 : [4], [5], [6], [3] => tails[0] = 3
     *
     * <p>len = 2 : [4, 5], [5, 6] =>tails[1] = 5
     *
     * <p>len = 3 : [4, 5, 6] => tails[2] = 6
     *
     * <p>对于一个元素 x，
     *
     * <p>如果它大于 tails 数组所有的值，那么把它添加到 tails 后面，表示最长递增子序列长度加1； 如果 tails[i-1] < x <= tails[i]，那么更新
     * tails[i-1] = x。 可以看出 tails 数组保持有序，因此在查找 Si 位于 tails 数组的位置时就可以使用二分查找。
     */
    /**
     * if (nums[i] > tails[len - 1]) { tails[len++] = nums[i]; } else { int index =
     * binarySearch(tails, 0, len - 1, nums[i]); tails[index] = nums[i]; }
     */
    public static int lengthOfLIS2(int[] nums) {
        int n = nums.length;
        int[] tails = new int[n]; // 记录最长递增子序列值最小的序列的最后一个数
        int size = 0;
        for (int i = 0; i < n; i++) {
            int index = binarySearch(tails, 0, size, nums[i]);
            tails[index] = nums[i];
            if (index == size) size++;
        }
        return size;
    }

    /** 二分查找 */
    private static int binarySearch(int[] nums, int left, int right, int key) {
        while (left < right) {
            int mid = left + (right - left) / 2; // 防止溢出
            if (nums[mid] == key) return mid;
            else if (nums[mid] > key) right = mid; // 对于while中含等号，则此处是-1
            else left = mid + 1;
        }
        return left;
    }
}
