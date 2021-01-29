/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class ValidMountainArray {
    public boolean validMountainArray(int[] arr) {
        if (arr.length < 3) {
            return false;
        }
        if (arr[0] > arr[1]) {
            return false;
        }

        int pointer = -1;
        boolean toTop = false;
        for (int i = 1; i < arr.length; i++) {
            pointer++;
            if (arr[pointer] == arr[i]) {
                return false;
            }

            if (!toTop) {
                if (arr[pointer] < arr[i]) {
                    continue;
                } else {
                    toTop = true;
                }
            } else {
                if (arr[pointer] > arr[i]) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        if (toTop) {
            return true;
        } else {
            return false;
        }
    }
}
