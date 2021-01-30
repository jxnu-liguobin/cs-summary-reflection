/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

import java.util.HashMap;
import java.util.Map;

class KthSymbolInGrammar {
    /*
    row 1: 0
    row 2: 01
    row 3: 0110
    row 4: 01101001
    row 5: 01101001 10010110

    My hack way: I can just get row30. N is not import, just use K as index
    */

    public static Map<Integer, int[]> cache = new HashMap<>();

    public int kthGrammar(int N, int K) {
        if (N == 0) {
            return 0;
        } else if (N == 1) {
            if (K == 1) {
                return 0;
            } else {
                return 1;
            }
        }
        int relateNum = kthGrammar(N - 1, (K + 1) / 2);
        if (relateNum == 1) {
            if (K % 2 == 1) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (K % 2 == 1) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
