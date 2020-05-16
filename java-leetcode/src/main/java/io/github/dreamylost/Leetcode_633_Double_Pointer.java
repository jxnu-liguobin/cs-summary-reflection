package io.github.dreamylost;

/**
 * @author 梦境迷离
 * @description 判断一个数是否为两个数的平方和，例如 5 = 1^2 + 2^2。 双指针
 * @time 2018年4月7日
 */
public class Leetcode_633_Double_Pointer {
    public boolean judgeSquareSum(int c) {
        // 最大不超过根号c
        int left = 0, right = (int) Math.sqrt(c);
        while (left <= right) {
            int powSum = left * left + right * right;
            if (powSum == c) return true;
            else if (powSum > c) right--;
            else left++;
        }
        return false;
    }
}
