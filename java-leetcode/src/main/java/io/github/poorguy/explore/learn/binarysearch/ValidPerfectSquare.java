/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

class ValidPerfectSquare {
    public boolean isPerfectSquare(int num) {
        long l = 0L;
        long r = num;
        while (l <= r) {
            long mid = (l + r) / 2;
            // !!!!!!!!!!!!!!!!!!!mid*mid might overflow if it is int, then the calculate of new l,r
            // will be wrong
            if (mid * mid == num) {
                return true;
            } else if (mid * mid > num) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return false;
    }
}
