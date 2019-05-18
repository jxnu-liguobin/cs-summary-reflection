package cn.edu.jxnu.tooffer;

/**
 * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
 */
public class T10 {

    /**
     * 依旧是斐波那契数列
     * 2*n的大矩形，和n个2*1的小矩形
     * 其中target*2为大矩阵的大小
     *
     * @param target
     * @return
     */
    public int RectCover(int target) {
        if (target <= 0) {
            return 0;
        }
        if (target == 1 || target == 2) {
            return target;
        }
        return this.RectCover(target - 1) + this.RectCover(target - 2);
    }
}
