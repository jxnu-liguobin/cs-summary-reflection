/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 把只包含因子2、3和5的数称作丑数（Ugly Number）。 例如6、8都是丑数，但14不是，因为它包含因子7。 习惯上我们把1当做是第一个丑数。
 *     求按从小到大的顺序的第N个丑数。
 * @author Mr.Li
 */
public class GetUglyNumber {

    public static void main(String[] args) {
        int s = new GetUglyNumber().GetUglyNumber_Solution(25);
        System.out.println(s);
    }

    public int GetUglyNumber_Solution(int index) {
        if (index == 0) return 0;
        java.util.ArrayList<Integer> res = new java.util.ArrayList<Integer>();
        res.add(1);
        int i2 = 0, i3 = 0, i5 = 0;
        while (res.size() < index) {
            // 2^x+3^y+5^z  x,y,z=(1, )
            // 求第index个最小的数字 该数字因式分解后仅由2,3,5构成
            int m2 = res.get(i2) * 2;
            int m3 = res.get(i3) * 3;
            int m5 = res.get(i5) * 5;
            // 数组存放最小的丑数
            int min = Math.min(m2, Math.min(m3, m5));
            res.add(min);
            // 以较小者为基准进行增加
            if (min == m2) i2++;
            if (min == m3) i3++;
            if (min == m5) i5++;
        }
        return res.get(res.size() - 1);
    }
}
