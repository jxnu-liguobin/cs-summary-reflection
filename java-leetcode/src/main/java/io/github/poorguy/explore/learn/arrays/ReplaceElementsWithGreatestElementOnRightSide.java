/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.arrays;

class ReplaceElementsWithGreatestElementOnRightSide {
    public int[] replaceElements(int[] arr) {
        int largest = arr[arr.length - 1];
        for (int i = arr.length - 1; i > -1; i--) {
            int oldValue = arr[i];
            arr[i] = largest;
            if (oldValue > largest) {
                largest = oldValue;
            }
        }
        arr[arr.length - 1] = -1;
        return arr;
    }
}
