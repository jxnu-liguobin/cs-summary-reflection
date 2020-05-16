package io.github.dreamylost.practice;

/**
 * 用 O(1) 时间检测整数 n 是否是 2 的幂次。
 *
 * @author 梦境迷离
 * @time 2018年8月10日
 * @version v1.0
 */
public class O1checkPowerOf2 {

    public boolean checkPowerOf2(int n) {
        if (n <= 0) {
            return false;
        }
        return (n & (n - 1)) == 0;
    }
}
