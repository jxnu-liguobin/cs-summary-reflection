/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch;

import java.util.ArrayList;
import java.util.List;

class FindKClosestElements {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // find the very close index
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > x) {
                right = mid - 1;
            } else if (arr[mid] < x) {
                left = mid + 1;
            } else {
                left = mid;
                break;
            }
        }
        if (left - 1 >= 0) {
            if (Math.abs(arr[left - 1] - x) <= Math.abs(arr[left] - x)) {
                left--;
            }
        }
        if (left + 1 < arr.length) {
            if (Math.abs(arr[left + 1] - x) < Math.abs(arr[left]) - x) {
                left++;
            }
        }

        int leftIndex = left;
        int rightIndex = left;
        int closestCount = 1;
        while (closestCount < k) {
            closestCount++;
            if (leftIndex - 1 == -1) {
                rightIndex += 1;
                continue;
            }
            if (rightIndex + 1 == arr.length) {
                leftIndex -= 1;
                continue;
            }
            if (Math.abs(arr[leftIndex - 1] - x) == Math.abs(arr[rightIndex + 1] - x)) {
                leftIndex -= 1;
                continue;
            }
            if (Math.abs(arr[leftIndex - 1] - x) < Math.abs(arr[rightIndex + 1] - x)) {
                leftIndex -= 1;
                continue;
            }
            rightIndex += 1;
        }
        int[] result = new int[k];
        System.arraycopy(arr, leftIndex, result, 0, k);
        List<Integer> resultList = new ArrayList<>(k);
        for (int val : result) {
            resultList.add(val);
        }
        return resultList;
    }
}
