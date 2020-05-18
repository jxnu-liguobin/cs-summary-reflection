package io.github.dreamylost.practice;

/**
 * @ClassName: Find.java
 *
 * @author Mr.Li @Description: TODO(这里用一句话描述这个方法的作用) @Date 2018-1-21 上午9:47:21
 * @version V1.0
 */
public class Find {

    /** @param args */
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        boolean bo = find(a, 5);
        System.out.println(bo);
    }

    /**
     * @description 杨氏矩阵查找算法
     * @param array
     * @param target
     * @return
     */
    public static boolean find(int[][] array, int target) {
        int row = 0;
        int col = array[0].length - 1;
        while (row <= array.length - 1 && col >= 0) {
            if (target == array[row][col]) return true;
            else if (target > array[row][col]) row++;
            else col--;
        }
        return false;
    }

    /**
     * @description 二分查找 时间复杂度nlogn
     * @param array
     * @param target
     * @return
     */
    public boolean Find2(int[][] array, int target) {

        for (int i = 0; i < array.length; i++) {
            int low = 0;
            int high = array[i].length - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (target > array[i][mid]) low = mid + 1;
                else if (target < array[i][mid]) high = mid - 1;
                else return true;
            }
        }
        return false;
    }
}
