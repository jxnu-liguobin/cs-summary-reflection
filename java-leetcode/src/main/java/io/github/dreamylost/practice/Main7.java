/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.Scanner;

/**
 * Title: Main7.java
 *
 * <p>Description:
 *
 * <p>小易正在玩一款新出的射击游戏,这个射击游戏在一个二维平面进行,小易在坐标原点(0,0),平面上有n只怪物,每个怪物有所在的坐标(x[i],
 * y[i])。小易进行一次射击会把x轴和y轴上(包含坐标原点)的怪物一次性消灭。 小易是这个游戏的VIP玩家,他拥有两项特权操作: 1、让平面内的所有怪物同时向任意同一方向移动任意同一距离
 * 2、让平面内的所有怪物同时对于小易(0,0)旋转任意同一角度 重点： 以上等价可以在十字架上的点最多有多少 小易要进行一次射击。小易在进行射击前,可以使用这两项特权操作任意次。
 * 小易想知道在他射击的时候最多可以同时消灭多少只怪物,请你帮帮小易。
 *
 * <p>所有点对于坐标原点(0,0)顺时针或者逆时针旋转45°,可以让所有点都在坐标轴上,所以5个怪物都可以消灭。
 *
 * <p>输入描述:
 *
 * <p>输入包括三行。 第一行中有一个正整数n(1 ≤ n ≤ 50),表示平面内的怪物数量。 第二行包括n个整数x[i](-1,000,000 ≤ x[i] ≤
 * 1,000,000),表示每只怪物所在坐标的横坐标,以空格分割。 第二行包括n个整数y[i](-1,000,000 ≤ y[i] ≤
 * 1,000,000),表示每只怪物所在坐标的纵坐标,以空格分割。
 *
 * <p>输出描述:
 *
 * <p>输出一个整数表示小易最多能消灭多少只怪物。
 *
 * <p>Copyright: Copyright (c) 2018
 *
 * <p>School: jxnu
 *
 * @author Mr.Li
 * @date 2018-2-16
 * @version 1.0
 */
public class Main7 {
    /**
     * 四个for循环。每个for循环选取一个点（判断该点不同于前面的点）， 前三个点要求不共线。      前两个点A,B通过第一条直线；         第三个点C通过另一条直线；
     *              第四个for循环，对于剩下的n-3个点，判断是否落在这两条直线上。如果有AD与AB平行 ， 则落在第一条直线上；如果有CD与AB垂直，则落在第二条直线上。
     *             第四个for循环结束，可以知道这两条直线能穿过最多几个点，每次更新最大值。 所有循环结束，输出最终的最大值即可。 计算斜率来判断平行和垂直，即dx1 * dy2
     * == dy1 *dx2。
     */
    /** @param args */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int x[] = new int[n];
        int y[] = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scan.nextInt();
        }
        for (int i = 0; i < n; i++) {
            y[i] = scan.nextInt();
        }
        scan.close();
        int maxShoot = 0; // 在坐标轴上的点
        if (n < 4) maxShoot = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int X1 = x[j] - x[i];
                int Y1 = y[j] - y[i];
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    int count = 3;
                    for (int l = 0; l < n; l++) {
                        if (l == i || l == j || l == k) {
                            continue;
                        }
                        int X2 = x[l] - x[k];
                        int Y2 = y[l] - y[k];
                        int X3 = x[l] - x[i];
                        int Y3 = y[l] - y[i];
                        if (X1 * X2 + Y1 * Y2 == 0 || X1 * Y3 == X3 * Y1) {
                            count++;
                        }
                    }
                    if (count > maxShoot) {
                        maxShoot = count;
                    }
                }
            }
        }
        System.out.println(maxShoot);
    }
}
