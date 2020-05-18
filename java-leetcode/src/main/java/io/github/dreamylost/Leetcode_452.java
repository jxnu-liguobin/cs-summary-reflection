package io.github.dreamylost;

import java.util.Arrays;

/**
 * @author 梦境迷离
 * @description 在二维空间中有许多球形的气球。对于每个气球，提供的输入是水平方向上，气球直径的开始和结束坐标。由于它是水平的，所以y坐标并不重要
 *     ，因此只要知道开始和结束的x坐标就足够了。开始坐标总是小于结束坐标。平面内最多存在104个气球。
 *     <p>一支弓箭可以沿着x轴从不同点完全垂直地射出。在坐标x处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足 xstart ≤ x ≤
 *     xend，则该气球会被引爆。可以射出的弓箭的数量没有限制。 弓箭一旦被射出之后，可以无限地前进。我们想找到使得所有气球全部被引爆，所需的弓箭的最小数量。 Input:
 *     [[10,16],[2,8], [1,6], [7,12]] Output: 2
 * @time 2018年3月31日
 */
public class Leetcode_452 {
    public static void main(String[] args) {
        int[][] aa = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        Arrays.sort(aa, (a, b) -> (a[1] - b[1]));
        Arrays.stream(aa)
                .forEach(
                        a -> {
                            Arrays.stream(a).forEach(b -> System.out.print(b));
                            System.out.println();
                        });
    }

    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) return 0;
        // 排序
        Arrays.sort(points, (a, b) -> (a[1] - b[1]));
        // 初始curPos=6;判断在2-6之间的之间跳过，因为球必破
        int curPos = points[0][1];
        int ret = 1;
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] <= curPos) {
                continue;
            }
            curPos = points[i][1];
            ret++;
        }
        return ret;
    }
}
