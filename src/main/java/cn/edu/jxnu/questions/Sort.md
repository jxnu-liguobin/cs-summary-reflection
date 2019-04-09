## 排序算法

排序算法可以分为内部排序和外部排序，内部排序是数据记录在内存中进行排序，而外部排序是因排序的数据很大，一次不能容纳全部的排序记录，在排序过程中需要访问外存。
常见的内部排序算法有：插入排序、希尔排序、选择排序、冒泡排序、归并排序、快速排序、堆排序、基数排序（不讨论细节）、计数等。

通用测试数据

```java
public abstract class Constant {

	public static final Object[] array = { 8, 34, 64, 51, 33, 22, 44, 55, 88, 1, 0, 2, 2 };
	// 有序数组
	public static final Object[] array2 = { 0, 1, 2, 2, 8, 22, 33, 34, 44, 51, 55, 64, 88 };
	public static final int len = array.length;

	public static void printResult(Object[] array) throws Exception {
		if (array == null || array.length == 0)
			throw new Exception("no element or invalid element in array");
		// java 8 lambda
		System.out.println(Arrays.stream(array).map(x -> x.toString())
		.collect(Collectors.joining(",", "[", "]")));
	}

	/**
	 * 原始版
	 * 
	 * @param array
	 * @param len
	 * @return
	 */
	public abstract Object[] sort(Object[] array, int len);

	/**
	 * 优化版
	 * 
	 * @param array
	 * @param len
	 * @return
	 */
	public Object[] sort2(Object[] array, int len) {
		return new Object[0];
	}
}

```

### 1. 插入排序

插入排序是一种最简单直观的排序算法，它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。

插入排序示意图

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/insert-sort.gif)

算法步骤：

1. 将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列。
2. 从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置。（如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面。）

```java
public class InsertionSort extends Constant {
	public static void main(String[] args) throws Exception {
		Constant.printResult(new InsertionSort().sort(Constant.array, Constant.len));
	}

	@Override
	public Object[] sort(Object[] array, int len) {
		int j, p;
		int temp = 0;
		for (p = 1; p < len; p++) {// 外层循环固定是len-1趟
			temp = (int) array[p];
			for (j = p; j > 0 && temp < (int) array[j - 1]; j--) {
				array[j] = array[j - 1];// j后移，j--
			}
			array[j] = temp;// 找到位置，插入
		}
		return array;
	}
}

```

### 2. 希尔排序 

也称递减增量排序算法，是插入排序的一种更高效的改进版本。
先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，待整个序列中的记录“基本有序”时，再对全体记录进行依次直接插入排序。

希尔排序示意图

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/shellsort_anim.gif)

算法步骤：

1. 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
2. 按增量序列个数k，对序列进行k 趟排序；
3. 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。

```java
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
					if (temp < (int) array[j - increment])
						array[j] = array[j - increment];
					else
						break;
				}
				array[j] = temp;
			}

		}
		return array;
	}

}
 
```

### 3. 选择排序

选择排序(Selection sort)也是一种简单直观的排序算法。其思想是每次选取最大（最小）的元素放到排序序列的起始位置

选择排序示意图

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/selection_sort_animation.gif)

算法步骤：

1. 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置
2. 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
3. 重复第二步，直到所有元素均排序完毕。

```java
public class SelectSort extends Constant {

	public static void main(String[] args) throws Exception {
		Constant.printResult(new SelectSort().sort(Constant.array, Constant.len));

	}

	@Override
	public Object[] sort(Object[] array, int len) {
		select(array, len);
		return array;

	}

	public void select(Object a[], int length) {
		if (a == null || length <= 0) {
			return;
		}
		int minary; // 定义一个最小坐标
		int temp = 0; // 定义一个临时变量
		for (int i = 0; i < length - 1; i++) {
			minary = i; // 将最小下标附一个初始值
			for (int j = i + 1; j < length; j++) { // 遍历无序区 的元素
				if ((int) a[j] < (int) a[minary]) {
					minary = j;
				}
			}
			if (minary != i) {
				temp = (int) a[i];
				a[i] = a[minary];
				a[minary] = temp;
			}
		}
	}

}

```

### 4. 冒泡排序

 冒泡排序（Bubble Sort）也是一种简单直观的排序算法。它重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。
 走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。

冒泡排序示意图

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/bubble_sort_animation.gif)

算法步骤：

1. 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
3. 针对所有的元素重复以上的步骤，除了最后一个。
4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。

```java
public class BubbleSort extends Constant {

	private static long time = 0l;
	private static long time2 = 0l;

	public static void main(String[] args) throws Exception {
	    // array:13474,array2:12832 相差明显
		Constant.printResult(new BubbleSort().sort(Constant.array2, Constant.len));
		// array:1284,array2:1283
		Constant.printResult(new BubbleSort().sort2(Constant.array2, Constant.len));
		System.out.println("没有优化：" + time);
		System.out.println("优化：" + time2);
	}

	/**
	 * 原始版
	 */
	@Override
	public Object[] sort(Object[] array, int len) {
		long t = System.nanoTime();
		for (int i = 0; i < array.length - 1; i++) {// 外层循环控制排序趟数
			for (int j = 0; j < array.length - 1 - i; j++) {// 内层循环控制每一趟排序多少次
				if ((int) array[j] > (int) array[j + 1]) {
					Object temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
		time = System.nanoTime() - t;
		return array;
	}

	/**
	 * 优化版
	 */
	@Override
	public Object[] sort2(Object[] array, int len) {
		long t = System.nanoTime();
		boolean flag = false;
		for (int i = 0; i < array.length; i++) {
			flag = true;
			// 思路是每次进下一趟冒泡的时候给flag设置true,如果被修改说明还有元素没有被排序，继续重复操作
			// 如果经过一趟下来，没有元素被交换【没有设置flag=false】,此时说明元素全部有序，直接退出
			for (int j = 0; j < array.length - 1 - i; j++) {
				if ((int) array[j] > (int) array[j + 1]) {
					Object temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					flag = false;
				}
			}
			if (flag) {
				break;
			}
		}
		time2 = System.nanoTime() - t;
		return array;
	}

}

```

### 5. 归并排序

归并排序（Merge sort）是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。

归并排序示意图

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/merge_sort_animation2.gif)

算法步骤：

1. 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
2. 设定两个指针，最初位置分别为两个已经排序序列的起始位置
3. 比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置
4. 重复步骤3直到某一指针达到序列尾
5. 将另一序列剩下的所有元素直接复制到合并序列尾

```java
public class MergeSort extends Constant {

	public static void main(String[] args) throws Exception {
		Constant.printResult(new MergeSort().sort(Constant.array, Constant.len));
	}

	@Override
	public Object[] sort(Object[] array, int len) {
		Object[] t = new Object[len];
		mSort(array, t, 0, len - 1);
		return array;
	}

	public void mSort(Object[] arr, Object[] temp, int left, int right) {
		int center;
		if (left < right) {
			center = (left + right) / 2;
			// 对前半部分进行排序
			mSort(arr, temp, left, center);
			// 对后半部分进行排序
			mSort(arr, temp, center + 1, right);
			// 合并
			// leftPos左半边的开始
			// rightPos右半边的开始
			merge(arr, temp, left, center + 1, right);
		}
	}

	private void merge(Object[] arr, Object[] tempArray, int leftPos, int rightPos, int rightEnd) {
		int i, leftEnd, numElements, tempPos;
		leftEnd = rightPos - 1;
		tempPos = leftPos;
		numElements = rightEnd - leftPos + 1;
		// 主循环
		while (leftPos <= leftEnd && rightPos <= rightEnd)
			if ((int) arr[leftPos] <= (int) arr[rightPos])
				tempArray[tempPos++] = arr[leftPos++];
			else
				tempArray[tempPos++] = arr[rightPos++];
		// 复制第一个数组的剩余数据
		while (leftPos <= leftEnd)
			tempArray[tempPos++] = arr[leftPos++];
		// 复制第二个数组的剩余数据
		while (rightPos <= rightEnd)
			tempArray[tempPos++] = arr[rightPos++];
		// 把临时数组拷贝回来
		for (i = 0; i < numElements; i++, rightEnd--) {
			arr[rightEnd] = tempArray[rightEnd];
		}
	}
}

```

### 6. 快速排序

快速排序是由东尼·霍尔所发展的一种排序算法。在平均状况下，排序 n 个项目要Ο(n log n)次比较。在最坏状况下则需要Ο(n2)次比较，但这种状况并不常见。事实上，快速排序通常明显比其他Ο(n log n) 算法更快，因为它的内部循环（inner loop）可以在大部分的架构上很有效率地被实现出来。
快速排序使用分治法（Divide and conquer）策略来把一个串行（list）分为两个子串行（sub-lists）。

快速排序示意图

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/Sorting_quicksort_anim.gif)

算法步骤：

1. 从数列中挑出一个元素，称为 “基准”（pivot），
2. 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
3. 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
递归的最底部情形，是数列的大小是零或一，也就是永远都已经被排序好了。虽然一直递归下去，但是这个算法总会退出，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。

```java
public class QuickSort extends Constant {
	public static void main(String[] args) throws Exception {
		Constant.printResult(new QuickSort().sort(Constant.array, Constant.len));
	}

	@Override
	public Object[] sort(Object[] array, int len) {

		quick(array, 0, len - 1);
		return array;
	}

	public Object[] quick(Object[] list, int low, int high) {
		if (low < high) {
			int middle = QuickSort.quickSortBy3Integers(list, low, high);// 三数中值分割法
			// int middle = quickSort(list, low, high); // 将list数组进行一分为二
			quick(list, low, middle - 1); // 对低字表进行递归排序
			quick(list, middle + 1, high); // 对高字表进行递归排序
			// 快速选择排序，这里需要if判断一下，少一次递归
		}
		return list;
	}

	// 一般方法
	public int quickSort(Object[] list, int low, int high) {
		int tmp = (int) list[low]; // 数组的第一个作为中轴
		while (low < high) {
			while (low < high && (int) list[high] >= tmp) {
				high--;
			}
			list[low] = list[high]; // 比中轴小的记录移到低端
			while (low < high && (int) list[low] <= tmp) {
				low++;
			}
			list[high] = list[low]; // 比中轴大的记录移到高端
		}
		list[low] = tmp; // 中轴记录到尾
		return low;
	}

	// 使用三数中值
	public static int quickSortBy3Integers(Object[] array, int low, int high) {
		// 三数取中
		int mid = low + (high - low) / 2;
		if ((int) array[mid] > (int) array[high]) {
			swap(array[mid], array[high]);
		}
		if ((int) array[low] > (int) array[high]) {
			swap(array[low], array[high]);
		}
		if ((int) array[mid] > (int) array[low]) {
			swap(array[mid], array[low]);
		}
		int key = (int) array[low];

		while (low < high) {
			while ((int) array[high] >= key && high > low) {
				high--;
			}
			array[low] = array[high];
			while ((int) array[low] <= key && high > low) {
				low++;
			}
			array[high] = array[low];
		}
		array[high] = key;
		return high;
	}

	private static void swap(Object a, Object b) {
		Object temp = a;
		a = b;
		b = temp;
	}
}

```

### 7. 堆排序

堆排序（Heapsort）是指利用堆这种数据结构所设计的一种排序算法。堆积是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或索引总是小于（或者大于）它的父节点。
堆排序的平均时间复杂度为Ο(nlogn) 。

堆排序示意图

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/Sorting_heapsort_anim.gif)

算法步骤：

1. 创建一个堆H[0..n-1]
2. 把堆首（最大值）和堆尾互换
3. 把堆的尺寸缩小1，并调用shift_down(0),目的是把新的数组顶端数据调整到相应位置
4. 重复步骤2，直到堆的尺寸为1

```java
public class HeapSort extends Constant {

	private static long time = 0l;
	private static long time2 = 0l;

	public static void main(String[] args) throws Exception {
		Constant.printResult(new HeapSort().sort(Constant.array, Constant.len));
		Constant.printResult(new HeapSort().sort2(Constant.array, Constant.len));
		// array1：48441,array2：46517 几乎相同
		System.out.println("耗费时间：" + time);
		// array1：23739,array2：27589 几乎相同，比未优化快，但偶尔出现很慢，未知原因
		System.out.println("优化耗费：" + time2);

	}

	/**
	 * 默认是小根堆
	 */
	PriorityQueue<Integer> queue = new PriorityQueue<Integer>(11, new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
	});

	/**
	 * 另外堆排序的比较次数和序列的初始状态有关，但只是在序列初始状态为堆的情况下比较次数显著减少，
	 * 在序列有序或逆序的情况下比较次数不会发生明显变化。
	 */
	@Override
	public Object[] sort(Object[] array, int len) {
		long t = System.nanoTime();
		for (int i = 0; i < array.length; i++) {
			queue.offer((int) array[i]);
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = queue.poll();
		}
		time = System.nanoTime() - t;
		return array;
	}

	@Override
	public Object[] sort2(Object array[], int n) {// 传入数组以及数组的大小
		long t = System.nanoTime();
		for (int i = (n - 2) / 2; i >= 0; i--) {// 对数组进行heapfiy产生最大值array[0]
			__shiftdown(array, n, i);// shiftdown所有可以下沉的父节点
		}
		for (int j = n - 1; j > 0; j--) {
			// 把每次产生的最大值调到数组的后面去
			int temp = (int) array[0];
			array[0] = array[j];
			array[j] = temp;
			__shiftdown(array, j, 0);

		}
		time2 = System.nanoTime() - t;
		return array;
	}

	private void __shiftdown(Object arr[], int n, int k) {// 下沉操作，传入数组以及需要排序的个数和下沉的索引
		int e = (int) arr[k];// 记录首个下沉的节点的值
		while (2 * k + 1 <= n - 1) {// 保证需要进行操作的节点至少有左孩子
			int maxindex = 2 * k + 1;// 假设最大的子节点为左孩子
			if (2 * k + 2 <= n - 1 && (int) arr[2 * k + 2] > (int) arr[maxindex]) {
			// 如果存在右孩子并且右孩子比左孩子大
				maxindex = 2 * k + 2;// 最大的子节点指向右孩子
			}
			if (e < (int) arr[maxindex]) {// 下沉节点的值要小于他的孩子节点
				arr[k] = arr[maxindex];// 下沉节点的值被大孩子的值替换
				k = maxindex;// 更新需要下沉节点的索引
			} else
				break;
		}
		arr[k] = e;// 最后不能下沉的节点赋值为首下沉节点的值
	}
}

```

### 8. 桶排序

算法思想：是将阵列分到有限数量的桶子里。每个桶子再个别排序（有可能再使用别的排序算法或是以递回方式继续使用桶排序进行排序）。桶排序是鸽巢排序的一种归纳结果。当要被排序的阵列内的数值是均匀分配的时候，桶排序使用线性时间（Θ（n））。但桶排序并不是比较排序，他不受到 O(n log n) 下限的影响。
简单来说，就是把数据分组，放在一个个的桶中，然后对每个桶里面的在进行排序。

例如要对大小为[1..1000]范围内的n个整数A[1..n]排序

首先，可以把桶设为大小为10的范围，具体而言，设集合B[1]存储[1..10]的整数，集合B[2]存储(10..20]的整数，……集合B[i]存储(   (i-1)*10,   i*10]的整数，i   =   1,2,..100。总共有  100个桶。

然后，对A[1..n]从头到尾扫描一遍，把每个A[i]放入对应的桶B[j]中。再对这100个桶中每个桶里的数字排序，这时可用冒泡，选择，乃至快排，一般来说任何排序法都可以。

最后，依次输出每个桶里面的数字，且每个桶中的数字从小到大输出，这样就得到所有数字排好序的一个序列了。

假设有n个数字，有m个桶，如果数字是平均分布的，则每个桶里面平均有n/m个数字。如果

对每个桶中的数字采用快速排序，那么整个算法的复杂度是

O(n   +   m   *   n/m*log(n/m))   =   O(n   +   nlogn   –   nlogm)

从上式看出，当m接近n的时候，桶排序复杂度接近O(n)

当然，以上复杂度的计算是基于输入的n个数字是平均分布这个假设的。这个假设是很强的  ，实际应用中效果并没有这么好。如果所有的数字都落在同一个桶中，那就退化成一般的排序了。

前面说的几大排序算法 ，大部分时间复杂度都是O（n2），也有部分排序算法时间复杂度是O(nlogn)。而桶式排序却能实现O（n）的时间复杂度。但桶排序的缺点是：

1）首先是空间复杂度比较高，需要的额外开销大。排序有两个数组的空间开销，一个存放待排序数组，一个就是所谓的桶，比如待排序值是从0到m-1，那就需要m个桶，这个桶数组就要至少m个空间。

2）其次待排序的元素都要在一定的范围内等等。



计数排序

```java
/**
 * @author 梦境迷离
 * @description 计数排序
 * 
 *              计数排序 计数排序适用数据范围 计数排序需要占用大量空间，它仅适用于数据比较集中的情况。比如
 *              [0~100]，[10000~19999] 这样的数据。
 *              最佳情况：T(n) = O(n+k) 最差情况：T(n) = O(n+k) 平均情况：T(n) = O(n+k)
 * 
 * @time 2018年4月8日
 */
public class CountSort extends Constant {

	private static long time = 0l;

	public static void main(String[] args) throws Exception {
		Constant.printResult(new CountSort().sort(Constant.array2, Constant.len));
		System.out.println("耗费时间："+time);//array1：18606,array2：18927 几乎相同

	}

	@Override
	public Object[] sort(Object[] array, int len) {

		return countSort(array);
	}

	/**
	 * 计数排序
	 * 
	 * 1.得到最大值与最小值，并计算最大值与最小值之差，制造桶，此时桶的下标i并非真正的元素，而是元素与min之差 
	 * 2.统计每个元素出现的次数，并记录在桶中
	 * 3.对桶中的元素进行取出，注意有重复的元素，而此时元素是i与min之和
	 * 
	 */
	public static Object[] countSort(Object[] arr) {
		long t = System.nanoTime();

		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		// 找出数组中的最大最小值
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, (int) arr[i]);
			min = Math.min(min, (int) arr[i]);
		}
		// 比max大1
		int help[] = new int[max - min + 1];
		// 找出每个数字出现的次数
		for (int i = 0; i < arr.length; i++) {
			int mapPos = (int) arr[i] - min;
			help[mapPos]++;
		}
		int index = 0;
		for (int i = 0; i < help.length; i++) {
			// 因为可能有重复的数据，所以需要循环
			while (help[i]-- > 0) {
				arr[index++] = i + min;
			}
		}

		time = System.nanoTime() - t;
		return arr;
	}
}

```

桶排序

```java
/**
 * @author 梦境迷离
 * @description 桶排序
 * 
 *              顺序从待排数组中取出下一个数字,此时2被取出,将其放入2号桶,是几就放几号桶
 * @time 2018年4月8日
 */
public class BucketSort extends Constant {

	public static void main(String[] args) throws Exception {
		Constant.printResult(new BucketSort().sort(Constant.array, Constant.len));

	}

	@Override
	public Object[] sort(Object[] array, int len) {

		return bucketSort(array);
	}

	/**
	 * @author 梦境迷离
	 * @return
	 * @description 1）待排序列的值处于一个可枚举的范围内 
	 *              2）待排序列所在可枚举范围不应太大，不然开销会很大。
	 *              桶排序的基本思想是： 把数组 arr 划分为n个大小相同子区间（桶），每个子区间各自排序，最后合并 。
	 *              计数排序是桶排序的一种特殊情况，可以把计数排序当成每个桶里只有一个元素的情况。
	 *              1.找出待排序数组中的最大值max、最小值min
	 *              2.我们使用 动态数组ArrayList 作为桶，桶里放的元素也用 ArrayList
	 *              存储。桶的数量为(max-min)/arr.length+1
	 *              3.遍历数组 arr，计算每个元素 arr[i] 放的桶
	 *              4.每个桶各自排序
	 *              5.遍历桶数组，把排序好的元素放进输出数组
	 * 
	 * @time 2018年4月8日
	 */
	public static Object[] bucketSort(Object[] arr) {

		/**
		 * 得到最值
		 */
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, (int) arr[i]);
			min = Math.min(min, (int) arr[i]);
		}
		// 桶数
		int bucketNum = (max - min) / arr.length + 1;
		ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
		for (int i = 0; i < bucketNum; i++) {
			bucketArr.add(new ArrayList<Integer>());
		}
		// 将每个元素放入桶
		for (int i = 0; i < arr.length; i++) {
			int num = ((Integer) arr[i] - (Integer) min) / (arr.length);
			bucketArr.get(num).add((Integer) arr[i]);
		}
		// 对每个桶进行排序
		for (int i = 0; i < bucketArr.size(); i++) {
			Collections.sort(bucketArr.get(i));
		}
		// System.out.println(bucketArr.toString());
		int i = 0;
		for (ArrayList<Integer> arrayList : bucketArr) {
			for (Integer integer : arrayList) {
				arr[i++] = integer;
				if (i == arr.length - 1)
					break;
			}
		}
		System.out.println("使用toString()方法：" + bucketArr.toString());
		return arr;

	}

}

```

基数排序

基数排序用于对多关键字域数据（例如：一副扑克牌，大小可以看做一个关键字域，花色也可以看做另一个关键字域）进行排序，每次对数据按一种关键字域进行排序
 ，然后将该轮排序结果按该关键字的大小顺序堆放，依次进行其他关键字域的排序，最后实现序列的整体排序。


### 9. 排序算法比较：

![](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/src/main/java/cn/edu/jxnu/practice/picture/sort_table.jpg)

### 10. 排序算法的下界和上界

1、基于比较的排序算法的最优下界为什么是O（nlogn）？

    可以使用决策二叉树证明
    
比较排序可以构造决策树，根节点代表原始序列a1,a2,a3……an，所有叶子节点都是这个序列的重排（共有n!个，其中有一个就是我们排序的结果a1',a2',a3'……an'）。
如果每次比较的结果都是等概率的话（恰好划分为概率空间相等的两个事件），那么二叉树就是高度平衡的，深度至少是log(n!)。
又因为log(n!)的增长速度与 nlogn 相同,即 log(n!)=Θ(nlogn)，这就是通用排序算法的最低时间复杂度O(nlogn)的依据。

2、假设A[1…n]是一个有n个不同数的数组。若i>j且A[i]<A[j]，则对偶（i，j）称为A的一个逆序对（inversion）

基于比较的排序的每次交换位置涉及一对逆序对，总共交换的次数就是逆序对的数量。即运行时间为：O(n+d)O(n+d)，n为数组大小，d为逆序对数目，一个n个元素的数组中逆序对数目最多为n(n−1)/2个。 
排序需要耗费的时间最少是逆序对的个数，但是对于不是基于比较的排序则不成立，比如基数，桶排序等。

### 11. 外排序

常见的外排序算法 - 多路归并

外排序算法的核心思路在于把文件分块读到内存，在内存中对每块文件依次进行排序，最后合并排序后的各块数据，依次按顺序写回文件。外排序需要进行多次磁盘读写，因此执行效率往往低于内排序，时间主要花费于磁盘读写上。我们给出外排序的算法步骤如下：
假设文件需要分成k块读入，需要从小到大进行排序。

1. 依次读入每个文件块，在内存中对当前文件块进行排序（应用恰当的内排序算法）。此时，每块文件相当于一个由小到大排列的有序队列。
2. 在内存中建立一个最小值堆，读入每块文件的队列头。
3.弹出堆顶元素，如果元素来自第i块，则从第i块文件中补充一个元素到最小值堆。弹出的元素暂存至临时数组。
4. 当临时数组存满时，将数组写至磁盘，并清空数组内容。
5. 重复过程（3）、（4），直至所有文件块读取完毕。

### 12. Collections.sort使用哪种排序算法？Arrays.sort()？

1、Arrays.sort()

底层调用DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0)，翻译过来就是双轴快速排序。

数组的长度小于QUICKSORT_THRESHOLD（286），使用双轴快速排序
数组长度小于INSERTION_SORT_THRESHOLD(值为47)的话，使用插入排序。
那如果大于286，它就会检测数组的连续升序和连续降序性好不好，如果好的话就用归并排序，不好的话就用快速排序

总结一下Arrays.sort()方法，如果数组长度大于等于286且连续性好的话，就用归并排序，如果大于等于286且连续性不好的话就用双轴快速排序。
如果长度小于286且大于等于47的话就用双轴快速排序，如果长度小于47的话就用插入排序。

2、Collections.sort()

底层调用归并排序legacyMergeSort(a, c)和TimSort()。

Timsort是结合了归并排序（merge sort）和插入排序（insertion sort）而得出的排序算法，它在现实中有很好的效率。
大体是说，Timsort是稳定的算法，当待排序的数组中已经有排序好的数，它的时间复杂度会小于n logn。与其他合并排序一样，Timesrot是稳定的排序算法，最坏时间复杂度是O（n log n）。
在最坏情况下，Timsort算法需要的临时空间是n/2，在最好情况下，它只需要一个很小的临时存储空间







以上的gif图片来自[博客园](https://www.cnblogs.com/RainyBear/p/5258483.html)