/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

/**
 * Forward declaration of guess API.
 *
 * @return -1 if num is lower than the guess number 1 if num is higher than the guess number
 *     otherwise return 0 int guess(int num);
 */
public class GuessNumberHigherOrLower extends GuessGame {
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int guess = guess(mid);
            if (guess == 0) {
                return mid;
            } else if (guess == -1) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
