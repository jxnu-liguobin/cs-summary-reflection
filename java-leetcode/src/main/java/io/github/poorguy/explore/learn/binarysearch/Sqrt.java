/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

class Sqrt {
    public int mySqrt(int x) {
        if (x * x == x) {
            return x;
        }
        long left = 1L;
        long right = x;
        long square;
        long possibleResult = x;
        while (left <= right) {
            long mid = (left + right) / 2;
            square = mid * mid;
            if (square == x) {
                return (int) mid;
            } else if (square < x) {
                possibleResult = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return (int) possibleResult;
    }
}
