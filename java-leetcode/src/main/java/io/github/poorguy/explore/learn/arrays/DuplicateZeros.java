/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class DuplicateZeros {
    public void duplicateZeros(int[] arr) {
        int[] newArr = new int[arr.length];
        int pointer = 0;
        for (int i = 0; i < arr.length; i++) {
            if (pointer >= arr.length) {
                break;
            }
            if (arr[i] != 0) {
                newArr[pointer] = arr[i];
                pointer++;
            } else {
                newArr[pointer] = 0;
                pointer++;
                if (pointer >= arr.length) {
                    break;
                }
                newArr[pointer] = 0;
                pointer++;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = newArr[i];
        }
    }
}
