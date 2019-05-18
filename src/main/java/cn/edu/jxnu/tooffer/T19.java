package cn.edu.jxnu.tooffer;

import java.util.ArrayList;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下矩阵：
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
 * 则依次打印出数字
 * 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 * 1  2  3  4
 * 5  6  7  8
 * 9  10 11 12
 * 13 14 15 16
 */
public class T19 {

    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return list;
        }
        print(matrix, 0, 0, matrix.length - 1, matrix[0].length - 1, list);
        return list;
    }

    public void print(int[][] matrix, int startRow, int startCol, int endRow, int endCol, ArrayList<Integer> list) {
        if (startRow < endRow && startCol < endCol) {
            for (int j = startCol; j <= endCol; j++) {
                list.add(matrix[startRow][j]);
            }
            for (int i = startRow + 1; i <= endRow - 1; i++) {
                list.add(matrix[i][endCol]);
            }
            for (int j = endCol; j >= startCol; j--) {
                list.add(matrix[endRow][j]);
            }
            for (int i = endRow - 1; i >= startRow + 1; i--) {
                list.add(matrix[i][startCol]);
            }
            print(matrix, startRow + 1, startCol + 1, endRow - 1, endCol - 1, list);
        } else if (startRow == endRow && startCol < endCol) {
            for (int j = startCol; j <= endCol; j++) {
                list.add(matrix[startRow][j]);
            }
        } else if (startRow < endRow && startCol == endCol) {
            for (int i = startRow; i <= endRow; i++) {
                list.add(matrix[i][endCol]);
            }
        } else if (startRow == endRow && startCol == endCol) {
            list.add(matrix[endRow][endCol]);
        }
    }

    public ArrayList<Integer> printMatrix2(int[][] array) {
        ArrayList<Integer> result = new ArrayList<>();
        if (array.length == 0) return result;
        int n = array.length, m = array[0].length;
        if (m == 0) return result;
        int layers = (Math.min(n, m) - 1) / 2 + 1;//这个是层数
        for (int i = 0; i < layers; i++) {
            for (int k = i; k < m - i; k++) result.add(array[i][k]);//左至右
            for (int j = i + 1; j < n - i; j++) result.add(array[j][m - i - 1]);//右上至右下
            for (int k = m - i - 2; (k >= i) && (n - i - 1 != i); k--) result.add(array[n - i - 1][k]);//右至左
            for (int j = n - i - 2; (j > i) && (m - i - 1 != i); j--) result.add(array[j][i]);//左下至左上
        }
        return result;
    }
}
