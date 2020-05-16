package io.github.dreamylost.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字， 例如，如果输入如下矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
 *     则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 * @author Mr.Li
 */
public class PrintMatrix {

    /**
     * @description 打印
     * @param list
     * @param col
     */
    public void show(List<?> list, int col) {
        System.out.println("des:");
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
            if (++j % col == 0) {
                System.out.println();
            }
        }
    }

    /**
     * @description 打印
     * @param list
     * @param col
     */
    public void show(int[][] arr, int col) {
        System.out.println("res");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] mar = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        // int[][]mar={{1,2,3,4,5,6}};//只有一行
        // int [][]mar={{1},{2},{3},{4},{5},{6}};
        new PrintMatrix().show(mar, 4);
        ArrayList<Integer> list = new PrintMatrix().printMatrix(mar);
        new PrintMatrix().show(list, 4);
    }

    /**
     * @note:不用MyfunctionForMatrix方法对单行单列也可用。对于不能处理您的加上即可
     * @description 来自牛客
     * @param array
     * @return
     */
    public ArrayList<Integer> printMatrix(int[][] array) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        //		result = (ArrayList<Integer>) MyfunctionForMatrix(array);
        //		if (result == null) {
        //			return null;
        //		} else if (result != null && result.get(0) == -1) {
        //			result.remove(0);
        //		}
        int n = array.length, m = array[0].length;
        int layers = (Math.min(n, m) - 1) / 2 + 1; // 这个是层数
        for (int i = 0; i < layers; i++) {
            for (int k = i; k < m - i; k++) {
                result.add(array[i][k]);
            } // 左至右
            for (int j = i + 1; j < n - i; j++) {
                result.add(array[j][m - i - 1]);
            } // 右上至右下
            for (int k = m - i - 2; (k >= i) && (n - i - 1 != i); k--) {
                result.add(array[n - i - 1][k]);
            } // 右至左
            for (int j = n - i - 2; (j > i) && (m - i - 1 != i); j--) {
                result.add(array[j][i]);
            } // 左下至左上
        }
        return result;
    }

    /**
     * @description 对于单行单列进行独立处理
     * @param matrix
     * @return
     */
    @SuppressWarnings("unused")
    private static List<?> MyfunctionForMatrix(int[][] matrix) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        if (matrix.length == 0) return arr;
        // 只有一行
        if (matrix.length == 1) {
            for (int i = 0; i < matrix[0].length; i++) {
                arr.add(matrix[0][i]);
            }
        }
        // 只有一列
        if (matrix[0].length == 1) {
            for (int i = 0; i < matrix.length; i++) {
                arr.add(matrix[i][0]);
            }
        } else {
            arr.add(-1);
        }
        return arr;
    }

    /**
     * @description 来自牛客
     * @param matrix
     * @return
     */
    public static ArrayList<Integer> printMatrix2(int[][] matrix) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        //		arr = (ArrayList<Integer>) MyfunctionForMatrix(matrix);
        //		if (arr == null) {
        //			return null;
        //		} else if (arr != null && arr.get(0) == -1) {
        //			arr.remove(0);
        //		}
        // 以下单行单列也可以。
        int up = 0;
        int left = 0;
        int down = matrix.length - 1;
        int right = matrix[0].length - 1;
        while (left <= right && up <= down) {

            for (int i = left; i <= right; i++) {
                arr.add(matrix[up][i]);
            }
            up++;
            for (int i = up; i <= down; i++) {
                arr.add(matrix[i][right]);
            }
            right--;
            if (up - 1 != down) {
                for (int i = right; i >= left; i--) {
                    arr.add(matrix[down][i]);
                }
            }
            down--;
            if (left != right + 1) {
                for (int i = down; i >= up; i--) {
                    arr.add(matrix[i][left]);
                }
            }
            left++;
        }
        return arr;
    }
}
