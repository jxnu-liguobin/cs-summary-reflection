/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.dp;

/**
 * 0-1 背包 有一个容量为 N 的背包，要用这个背包装下物品的价值最大，这些物品有两个属性：体积 w 和价值 v。
 *
 * <p>定义一个二维数组 dp 存储最大价值，其中 dp[i][j] 表示前 i 件物品体积不超过 j 的情况下能达到的最大价值。设第 i 件物品体积为 w， 价值为 v，根据第 i
 * 件物品是否添加到背包中，可以分两种情况讨论：
 *
 * <p>第 i 件物品没添加到背包，总体积不超过 j 的前 i 件物品的最大价值就是总体积不超过 j 的前 i-1 件物品的最大价值，dp[i][j] = dp[i-1][j]。
 *
 * <p>第 i 件物品添加到背包中，dp[i][j] = dp[i-1][j-w] + v。
 *
 * <p>第 i 件物品可添加也可以不添加，取决于哪种情况下最大价值更大。
 *
 * <p>综上，0-1 背包的状态转移方程为：
 *
 * @author 梦境迷离.
 * @time 2018年6月11日
 * @version v1.0
 */
public class Knapsack_Dp {

    public static int knapsack(int W, int N, int[] weights, int[] values) {

        // 其中 dp[i][j] 表示前 i 件物品体积不超过 j 的情况下能达到的最大价值[承重]
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            int w = weights[i - 1], v = values[i - 1]; // 计算当前物品体积和价值
            for (int j = 1; j <= W; j++) {
                // 放得下
                if (j > w) {
                    // 第i件物品可以添加也可以不添加，取最大值
                    // 表示当前状态下能放进背包里面的物品的最大总价值，dp[N][W]就是结果
                    /**
                     * 1、假如我们放进背包，dp[i][j] = dp[i - 1][j - weight[i]] + value[i]
                     *
                     * <p>这里的dp[i - 1][j -weight[i]] + value[i]
                     *
                     * <p>应该这么理解：在没放这件物品之前的状态值加上要放进去这件物品的价值。而对于dp[i - 1][j - weight[i]]
                     *
                     * <p>这部分，i-1很容易理解，关键是 j -weight[i]这里，
                     *
                     * <p>我们要明白：要把这件物品放进背包，就得在背包里面预留这一部分空间。
                     *
                     * <p>2、假如我们不放进背包，dp[i][j] = dp[i - 1][j]，这个很容易理解。
                     *
                     * <p>dp[i][j] = max(dp[i - 1][j] , dp[i - 1][j - weight[i]]+value[i])
                     *
                     * <p>当然，还有一种特殊的情况，就是背包放不下当前这一件物品，这种情况下dp[i][j] = f[i - 1][j]。
                     */
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w] + v);
                } else {
                    dp[i][j] = dp[i - 1][j]; // 放不下
                }
            }
        }
        return dp[N][W];
    }

    /**
     * 在程序实现时可以对 0-1 背包做优化。观察状态转移方程可以知道，前 i 件物品的状态仅由前 i-1 件物品的状态有关，
     *
     * <p>因此可以将 dp 定义为一维数组，其中 dp[j] 既可以表示 dp[i-1][j] 也可以表示 dp[i][j]。 此时dp[j]=max(dp[j],dp[j-w]+v)，因为
     * dp[j-w] 表示 dp[i-1][j-w]
     *
     * <p>因此不能先求dp[i][j-w]，以防止将 dp[i-1][j-w] 覆盖。
     *
     * <p>也就是说要先计算 dp[i][j] 再计算 dp[i][j-w]，在程序实现时需要按倒序来循环求解。
     */
    public int knapsack2(int W, int N, int[] weights, int[] values) {
        int[] dp = new int[W + 1];
        for (int i = 1; i <= N; i++) {
            int w = weights[i - 1], v = values[i - 1];
            for (int j = W; j >= 1; j--) {
                if (j >= w) {
                    dp[j] = Math.max(dp[j], dp[j - w] + v);
                }
            }
        }
        return dp[W];
    }
}
