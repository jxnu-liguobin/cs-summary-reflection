package cn.edu.jxnu.lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:CompletableFuture的流式调用，异常，异步
 * @Package: cn.edu.jxnu.lambda
 * @author: 梦境迷离
 * @date: 2018年3月27日 上午11:06:30
 */

public class CompletableFutureDemo2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 1、supplyAsync方法构造一个CompletableFuture实例
		// 2、supplyAsync在一个新的线程中执行传入的参数
		// 3、calu方法可能执行的很慢，但是不会影响CompletableFuture实例的构造速度，因此supplyAsync是立即返回的
		// 4、计算没有完成则get方法会等待
		final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calu(50));
		System.out.println(future.get());
		// CompletableFuture的工厂方法，supplyAsync用于需要返回值的场景，runAsync应用于没有返回值的场景
		// 都有一个方法可以接受Executor参数的方法，可以指定在自己的线程池中工作，如果不指定，则使用系统的ForkJoinPool.common线程池
		// ForkJoinPool.commonPool()
		// Java8新增，获取一公共的ForkJoin线程池，都是Daemon线程，这意味的如果主线程退出，无论这些线程池的任务是否执行完成，都会退出系统
		System.out.println("*********************流式调用****************");
		// 计算50*50，得到结果并将2500转化为字符串，再拼接两个引号，再打印该字符串
		CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> calu(50))
				.thenApply(i -> Integer.toString(i)).thenApply(str -> "\"" + str + "\"")
				.thenAccept(System.out::println);
		// 必须调用，否则主线程退出，所有的Daemon的线程都会退出，calu无法正常完成
		future2.get();
		System.out.println("*********************CompletableFuture异常处理****************");
		CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(() -> caluError(50)).exceptionally(ex -> {
			// 打印异常信息，返回一个默认值0
			System.out.println(ex.toString());
			return 0;
		}).thenApply(i -> Integer.toString(i)).thenApply(str -> "\"" + str + "\"").thenAccept(System.out::println);
		future3.get();
	}

	/**
	 * @description:不需要自动定义线程，只需要包装一个任务
	 */
	public static int calu(Integer param) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		return param * param;
	}

	/**
	 * 处理异常
	 */
	public static Integer caluError(Integer param) {
		return param / 0;
	}
}
