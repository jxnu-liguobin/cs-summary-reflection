package io.github.dreamylost.tooffer;

/**
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个函数，
 * 输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class T1 {

    public static boolean Find(int target, int[][] array) {
        int length = array.length - 1;
        int i = 0;
        while (length >= 0 && i < array[0].length) {
            if (array[length][i] > target) {
                length--;
            } else if (array[length][i] < target) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 1 2 3
     * 2 3 4
     * 3 4 5
     */
    public static void main(String[] args) {
        int[][] array = {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}};
        System.out.println(Find(5, array));
    }
}
