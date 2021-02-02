/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

class FindSmallestLetterGreaterThanTarget {
    public char nextGreatestLetter(char[] letters, char target) {
        int l = 0;
        int r = letters.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (letters[mid] - target > 0) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        if (r == letters.length - 1 && letters[r] <= target) {
            return letters[0];
        }
        return letters[r];
    }
}
