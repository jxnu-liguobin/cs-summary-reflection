/* All Contributors (C) 2020 */
package io.github.wkk.everyday.aug;

/**
 * 题目: 机器人能否返回原点 思路: 创建一个1*2的数组arr, left:arr[0] + 1 right:arr[0] - 1 up:arr[1]+1 down:arr[1]-1;
 *
 * @author kongwiki@163.com
 * @since 2020/8/28下午1:24
 */
public class RobotReturnToOrigin {
    public boolean judgeCircle(String moves) {
        int[] array = {0, 0};
        int count = 0;
        while (count < moves.length()) {
            if (moves.charAt(count) == 'L') {
                array[0] += 1;
            } else if (moves.charAt(count) == 'R') {
                array[0] -= 1;
            } else if (moves.charAt(count) == 'U') {
                array[1] += 1;
            } else {
                array[1] -= 1;
            }
            count++;
        }

        return array[0] == 0 && array[1] == 0;
    }
}
