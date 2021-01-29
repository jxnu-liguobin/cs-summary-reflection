/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class FindNumbersWithEvenNumberOfDigits {
    public int findNumbers(int[] nums) {
        int counter = 0;
        for (int num : nums) {
            int digitCount = String.valueOf(num).split("").length;
            if (digitCount % 2 == 0) {
                counter++;
            }
        }
        return counter;
    }
}
