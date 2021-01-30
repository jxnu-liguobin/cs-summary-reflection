/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

import java.util.ArrayList;
import java.util.List;

class GenerateParentheses {
    // the ")" must not be more than "(" before
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++) {
            if (result.isEmpty()) {
                result.add("(");
            } else {
                int size = result.size();
                for (int j = 0; j < size; j++) {
                    String str = result.get(j);
                    char[] chars = str.toCharArray();
                    int leftCount = 0;
                    int rightCount = 0;
                    for (char c : chars) {
                        if (c == '(') {
                            leftCount++;
                        } else {
                            rightCount++;
                        }
                    }

                    if (leftCount == n) {
                        result.set(j, str + ")");
                    } else if (leftCount == rightCount) {
                        result.set(j, str + "(");
                    } else {
                        result.set(j, str + "(");
                        result.add(str + ")");
                    }
                }
            }
        }
        return result;
    }
}
