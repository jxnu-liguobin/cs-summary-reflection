/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

import java.util.ArrayList;

/**
 * 每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友, 今年亦是如此。HF作为牛客的资深元老,自然也准备了一些小游戏。 其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。
 * 然后,他随机指定一个数m,让编号为0的小朋友开始报数。 每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,
 * 并且不再回到圈中,从他的下一个小朋友开始,继续0...m-1报数.... 这样下去....直到剩下最后一个小朋友,可以不用表演, 并且拿到牛客名贵的“名侦探柯南”典藏版(名额有限哦!!^_^)。
 * 请你试着想下,哪个小朋友会得到这份礼品呢？(注：小朋友的编号是从0到n-1)
 */
public class T46 {

    public int LastRemaining_Solution(int n, int m) {
        if (n == 0 || m == 0) {
            return -1;
        }
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            data.add(i);
        }
        int index = -1;
        while (data.size() > 1) {
            index = (index + m) % data.size();
            data.remove(index);
            index--;
        }
        return data.get(0);
    }

    /**
     * 约瑟夫问题 递推公式 让f[i]为i个人玩游戏报m退出最后的胜利者的编号，最后的结果自然是f[n] 服了 f[1] = 0; f[i] = (f[i - 1] + m) mod i;
     *
     * <p>因为递推，所以可以不用保存状态，每轮的序列中最后出序列的数都是同一个
     */
    public int LastRemaining_Solution2(int n, int m) {
        if (n < 1 || m < 1) return -1;
        if (n == 1) {
            return 0;
        }
        return (LastRemaining_Solution(n - 1, m) + m) % n;
    }
}
