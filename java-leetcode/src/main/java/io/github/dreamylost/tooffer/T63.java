package io.github.dreamylost.tooffer;

import java.util.LinkedList;

/**
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值， 那么中位数就是所有数值排序之后位于中间的数值。 如果从数据流中读出偶数个数值， 那么中位数就是所有数值排序之后中间两个数的平均值。
 * 我们使用Insert()方法读取数据流， 使用GetMedian()方法获取当前读取数据的中位数。
 */
public class T63 {

    LinkedList<Integer> list = new LinkedList<>();

    /**
     * 思路：构建一棵"平衡二叉搜索树 "。 每个结点左子树均是小于等于其value的值，右子树均大于等于value值。每个子树均按其 “结点数” 调节平衡。 这样根节点一定是中间值中的一个。
     * 若结点数为奇数，则返回根节点的值；若结点个数为偶数，则再从根结点左子数或右子数中个数较多的子树中选出最大或最小值既可。
     */
    public void Insert(Integer num) {
        if (list.size() == 0 || num < list.getFirst()) {
            list.addFirst(num);
        } else {
            boolean insertFlag = false;
            for (Integer e : list) {
                if (num < e) {
                    int index = list.indexOf(e);
                    list.add(index, num);
                    insertFlag = true;
                    break;
                }
            }
            if (!insertFlag) {
                list.addLast(num);
            }
        }
    }

    public Double GetMedian() {
        if (list.size() == 0) {
            return null;
        }
        if (list.size() % 2 == 0) {
            int i = list.size() / 2;
            Double a = (double) (list.get(i - 1) + list.get(i));
            return a / 2;
        }
        list.get(0);
        return Double.valueOf(list.get(list.size() / 2));
    }
}
