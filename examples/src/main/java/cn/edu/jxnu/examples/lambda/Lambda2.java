package cn.edu.jxnu.examples.lambda;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 梦境迷离
 * @description lambda表达式的demo
 * @time 2018年4月3日
 */
@SuppressWarnings("unused")
public class Lambda2 {

	@Test
	public void Test() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
		List<Integer> sameOrder = numbers.stream().collect(Collectors.toList());
		Assert.assertEquals(numbers, sameOrder);

		// 进去的流是无序，出去的也是无序集合
		Set<Integer> num = new HashSet<>(Arrays.asList(4, 3, 2, 1));
		// List<Integer> sameOr = num.stream().collect(Collectors.toList());
		List<Integer> sameOr = num.stream().sorted().collect(Collectors.toList());
		// Assert.assertEquals(Arrays.asList(4, 3, 2, 1), sameOr);
		Assert.assertEquals(Arrays.asList(1, 2, 3, 4), sameOr);
		// 仍是无序集合
		// List<Integer> stilOr = num.stream().map(x -> x +
		// 1).collect(Collectors.toList());
		// stilOr.stream().forEach(System.out::print);
		// System.out.println();
		// 一些操作有序操作开销更大，filter map reduce
		// 使用并行流的forach，但是可以使用forEachOrdered替代
		// num.stream().collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::print);
		// System.out.println();
		// 分组
		Map<Boolean, List<Integer>> sMap = numbers.stream().collect(Collectors.partitioningBy(x -> {
			if (x % 2 == 0) {
				return true;
			} else {
				return false;
			}
		}));
		// 遍历map
		sMap.forEach((k, v) -> {
			// 输出
			System.out.println("sMap : " + k + " Count : " + v);
		});
		// 拼接字符串
		// 设置分隔符，前缀，后缀
		String result = numbers.stream().map(x -> x.toString()).collect(Collectors.joining(",", "[", "]"));
		System.out.println(result);
		// map.computeIfAbsent 不存在则计算，可以用作缓存
		// 并行求和
		int sum = numbers.parallelStream().mapToInt(i -> i).sum();
		System.out.println(sum);
		// 初始化数组【改变了传入的数组】，并行化
		int[] array = new int[10];
		Arrays.parallelSetAll(array, i -> i);
		// 查看值，并能继续操作流【使用forEach记录中间值需要创建新的流，peak避免重复流操作，用途之一：记录日志】
		Set<Integer> set = numbers.stream().map(x -> x * x).peek(temp -> System.err.println("Temp:" + temp))
				.collect(Collectors.toSet());

	}

	/**
	 * 
	 * @author 梦境迷离
	 * @description 并行处理-求平方和
	 * @time 2018年4月4日
	 */
	public static int sumOfSquares(IntStream range) {
		return range.parallel().map(x -> x * x).sum();
	}

	/**
	 * 
	 * @author 梦境迷离
	 * @description 并行处理-列表数字相乘，结果乘5
	 * @time 2018年4月4日
	 */
	public static int multiplyThrough(List<Integer> numbers) {
		return 5 * numbers.parallelStream().reduce(1, (acc, x) -> x * acc);
	}

	private List<Integer> arrayListOfNumbers = new ArrayList<>();
	private List<Integer> linkedListOfNumbers = new LinkedList<>();

	/**
	 * 
	 * @author 梦境迷离
	 * @description 并行处理-求列表平方和-数组
	 * @time 2018年4月4日
	 */
	public int fastSumOfSquares() {
		return arrayListOfNumbers.parallelStream().mapToInt(x -> x * x).sum();
	}

	/**
	 * 
	 * @author 梦境迷离
	 * @description 串行处理-求列表平方和-链表
	 * @time 2018年4月4日
	 */
	public int serialFastSumOfSquares() {
		return arrayListOfNumbers.stream().mapToInt(x -> x * x).sum();
	}

	/**
	 * 
	 * @author 梦境迷离
	 * @description 串行-使用reduce求和
	 * @time 2018年4月4日
	 */
	public int serialSlowSumOfSquares() {
		return linkedListOfNumbers.stream().map(x -> x * x).reduce(0, (acc, x) -> acc + x);
	}

	/**
	 * 
	 * @author 梦境迷离
	 * @description 并行-使用reduce求和
	 * @time 2018年4月4日
	 */
	public int intermediateSumOfSquares() {
		return arrayListOfNumbers.parallelStream().map(x -> x * x).reduce(0, (acc, x) -> acc + x);
	}

	// 避免重复计算较小值多次
	static class Fibonacci {

		private final Map<Integer, Long> cache;

		public Fibonacci() {
			cache = new HashMap<>();
			cache.put(0, 0L);
			cache.put(1, 1L);
		}

		public long fibonacci(int x) {
			return cache.computeIfAbsent(x, n -> fibonacci(n - 1) + fibonacci(n - 2));
		}

	}

}
