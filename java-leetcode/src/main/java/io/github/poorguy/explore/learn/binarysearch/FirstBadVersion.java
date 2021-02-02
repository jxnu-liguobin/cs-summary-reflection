/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.binarysearch; /* The isBadVersion API is defined in the parent class VersionControl.
                                                      boolean isBadVersion(int version); */

public class FirstBadVersion extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 0;
        int right = n;
        Integer badIndex = null;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            boolean isBadVersion = isBadVersion(mid);
            if (isBadVersion) {
                badIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (badIndex == null) {
            return -1;
        } else {
            return badIndex;
        }
    }
}
