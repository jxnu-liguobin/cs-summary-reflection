/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PascalsTriangle2 {
    public List<Integer> getRow(int rowIndex) {
        if (rowIndex == 0) {
            return Arrays.asList(1);
        } else {
            List<Integer> lastRow = getRow(rowIndex - 1);
            List<Integer> thisRow = new ArrayList<>();
            thisRow.add(1);
            for (int i = 0; i < lastRow.size() - 1; i++) {
                thisRow.add(lastRow.get(i) + lastRow.get(i + 1));
            }
            thisRow.add(1);
            return thisRow;
        }
    }
}
