/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.sort;

import java.util.PriorityQueue;

/**
 * 使用优先级队列实现堆排序 总时间复杂度NlogN = 建堆O(N)+删除一个元素调整logN * N个元素 最多比较次数 2NlogN-O(N)，至少比较次数NlogN-O(N)
 *
 * <p>如果从底部最后的父节点开始建堆，那么我们可以大概算一下：假如有N个节点，那么高度为H=logN， 最后一层每个父节点最多只需要下调1次，倒数第二层最多只需要下调2次，顶点最多需要下调H次，
 * 而最后一层父节点共有2^(H-1)个,倒数第二层公有2^(H-2),顶点只有1(2^0)个，所以总共的时间复杂度为s = 1 * 2^(H-1) + 2 * 2^(H-2) + ... +
 * (H-1) * 2^1 + H * 2^0 将H代入后s= 2N - 2 - log2(N)，近似的时间复杂度就是O(N)。
 *
 * @author 梦境迷离
 * @since 2020年11月27日
 */
public class HeapSort extends Constant<Integer> {

    private static long time = 0L;
    private static long time2 = 0L;

    public static void main(String[] args) throws Exception {
        new HeapSort().sort(Constant.array, Constant.len);
        new HeapSort().sort2(Constant.array, Constant.len);
        System.out.println("耗费时间：" + time); // array1：48441,array2：46517 几乎相同
        System.out.println("优化耗费：" + time2); // array1：23739,array2：27589 几乎相同，比未优化快，但偶尔出现很慢，未知原因
    }

    /** 默认是小根堆 */
    PriorityQueue<Integer> queue = new PriorityQueue<>(11, Integer::compareTo);

    /** 另外堆排序的比较次数和序列的初始状态有关，但只是在序列初始状态为堆的情况下比较次数显著减少，在序列有序或逆序的情况下比较次数不会发生明显变化。 */
    @Override
    public void sort(Integer[] array, int len) {
        long t = System.nanoTime();
        for (Integer integer : array) {
            queue.offer(integer);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = queue.poll();
        }
        time = System.nanoTime() - t;
    }

    @Override
    public void sort2(Integer[] array, int n) throws Exception { // 传入数组以及数组的大小
        long t = System.nanoTime();
        for (int i = (n - 2) / 2; i >= 0; i--) { // 对数组进行heapfiy产生最大值array[0]
            __shiftdown(array, n, i); // shiftdown所有可以下沉的父节点
        }
        for (int j = n - 1; j > 0; j--) {
            // 把每次产生的最大值调到数组的后面去
            int temp = array[0];
            array[0] = array[j];
            array[j] = temp;
            __shiftdown(array, j, 0);
        }
        time2 = System.nanoTime() - t;
        super.printResult(array);
    }

    private void __shiftdown(Integer[] arr, int n, int k) { // 下沉操作，传入数组以及需要排序的个数和下沉的索引
        int e = arr[k]; // 记录首个下沉的节点的值
        while (2 * k + 1 <= n - 1) { // 保证需要进行操作的节点至少有左孩子
            int maxindex = 2 * k + 1; // 假设最大的子节点为左孩子
            if (2 * k + 2 <= n - 1 && arr[2 * k + 2] > (int) arr[maxindex]) { // 如果存在右孩子并且右孩子比左孩子大
                maxindex = 2 * k + 2; // 最大的子节点指向右孩子
            }
            if (e < arr[maxindex]) { // 下沉节点的值要小于他的孩子节点
                arr[k] = arr[maxindex]; // 下沉节点的值被大孩子的值替换
                k = maxindex; // 更新需要下沉节点的索引
            } else break;
        }
        arr[k] = e; // 最后不能下沉的节点赋值为首下沉节点的值
    }
}
