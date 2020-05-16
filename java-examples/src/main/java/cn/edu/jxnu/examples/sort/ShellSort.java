package cn.edu.jxnu.examples.sort;

/**
 * 时间复杂度theta(N^2) 【大O包含了等于，theta只是小于】 Hibbard 增量 最坏 时间复杂度theta(n^(2/3)) 证明极其复杂
 *
 * @time 2018年3月24日15:30:20
 */
public class ShellSort extends Constant {

    public static void main(String[] args) throws Exception {
        Constant.printResult(new ShellSort().sort(Constant.array, Constant.len));
    }

    @Override
    public Object[] sort(Object[] array, int len) {
        int i, j, increment;
        int temp;
        // 使用一种流行但不好的序列h=[h/2]下取整
        for (increment = len / 2; increment > 0; increment /= 2) {
            for (i = increment; i < len; i++) {
                temp = (int) array[i];
                for (j = i; j >= increment; j -= increment) {
                    if (temp < (int) array[j - increment]) array[j] = array[j - increment];
                    else break;
                }
                array[j] = temp;
            }
        }
        return array;
    }
}
