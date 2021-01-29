/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class SortArrayByParity {
    public int[] sortArrayByParity(int[] A) {
        int left = 0;
        int right = A.length - 1;
        while (left < right) {
            if (A[left] % 2 == 1 && A[right] % 2 == 0) {
                int t = A[left];
                A[left] = A[right];
                A[right] = t;
                left++;
                right--;
            }
            if (A[left] % 2 == 0) {
                left++;
            }
            if (A[right] % 2 == 1) {
                right--;
            }
        }
        return A;
    }
}
