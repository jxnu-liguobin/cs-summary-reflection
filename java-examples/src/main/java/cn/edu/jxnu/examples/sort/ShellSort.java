/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

/**
 * 时间复杂度theta(N^2) 【大O包含了等于，theta只是小于】 Hibbard 增量 最坏 时间复杂度theta(n^(2/3)) 证明极其复杂
 *
 * @author 梦境迷离
 * @since 2020年11月27日
 */
public class ShellSort extends Constant<Integer> {

    public static void main(String[] args) throws Exception {
        new ShellSort().sort(Constant.array, Constant.len);
    }

    @Override
    public void sort(Integer[] array, int len) throws Exception {
        int i, j, increment;
        int temp;
        // 使用一种流行但不好的序列h=[h/2]下取整
        for (increment = len / 2; increment > 0; increment /= 2) {
            for (i = increment; i < len; i++) {
                temp = array[i];
                for (j = i; j >= increment; j -= increment) {
                    if (temp < array[j - increment]) array[j] = array[j - increment];
                    else break;
                }
                array[j] = temp;
            }
        }
        super.printResult(array);
    }
}
