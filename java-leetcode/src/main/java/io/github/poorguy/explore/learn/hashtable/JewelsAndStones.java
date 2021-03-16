/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.hashtable;

class JewelsAndStones {
    public int numJewelsInStones(String jewels, String stones) {
        char[] jewelArray = jewels.toCharArray();
        char[] stoneArray = stones.toCharArray();
        boolean[] jewelFlag = new boolean[52];
        for (char c : jewelArray) {
            if (c - 'A' < 26) {
                jewelFlag[c - 'A'] = true;
            } else {
                jewelFlag[c - 'a' + 26] = true;
            }
        }

        int count = 0;
        for (char c : stoneArray) {
            if (c - 'A' < 26) {
                if (jewelFlag[c - 'A']) {
                    count++;
                }
            } else {
                if (jewelFlag[c - 'a' + 26]) {
                    count++;
                }
            }
        }
        return count;
    }
}
