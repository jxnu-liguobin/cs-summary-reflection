/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion2;

class SearchATwoDMatrix2 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = 0, n = matrix[0].length - 1;
        while (n > -1 && m < matrix.length) {
            if (matrix[m][n] == target) {
                return true;
            } else if (matrix[m][n] < target) {
                m++;
            } else {
                n--;
            }
        }
        return false;
    }

    // recurice solution takes too much time
    private boolean helper(int[][] matrix, int target, int startM, int startN) {
        if (startM == matrix.length || startN == matrix[0].length) {
            return false;
        } else if (matrix[startM][startN] == target) {
            return true;
        } else {
            return helper(matrix, target, startM + 1, startN)
                    || helper(matrix, target, startM, startN + 1);
        }
    }
}
