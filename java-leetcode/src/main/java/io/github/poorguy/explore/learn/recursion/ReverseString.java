/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

class ReverseString {
    public void reverseString(char[] s) {
        old(s);
    }

    public void old(char[] s) {
        if (s.length == 0 || s.length == 1) {
            return;
        }

        int left = 0;
        int right = s.length - 1;
        char t;
        while (left < right) {
            t = s[left];
            s[left] = s[right];
            s[right] = t;
            left++;
            right--;
        }
    }
}
