package cn.edu.jxnu.lambda;

import java.util.concurrent.CompletableFuture;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:
 * @Package: cn.edu.jxnu.lambda
 * @author: 梦境迷离
 * @date: 2018年3月27日 上午10:03:02
 */

public class CompletableFutureDemo {

	public static void main(String[] args) throws InterruptedException {
		final CompletableFuture<Integer> future = new CompletableFuture<>();
		new Thread(new AskThread(future)).start();
		// 模拟长时间计算过程
		Thread.sleep(1000);
		// 告知完成结果
		future.complete(60);
		// 开始继续进行计算平方
	}

	/**
	 * CompletableFuture 实现了Future接口和CompletionStage接口
	 * CompletionStage接口拥有很多方法，为了流式调用 形如
	 * stage.thenApply(x->square(x)).thenAccept(x->System.out::println(x)).thenRun(()->System.out.println())
	 */

	// 计算re表示的数字的平方，将其打印
	static class AskThread implements Runnable {
		CompletableFuture<Integer> re = null;

		public AskThread(CompletableFuture<Integer> re) {
			this.re = re;
		}

		@Override
		public void run() {
			int myRe = 0;
			try {
				// 没有传入int数字之前会阻塞
				myRe = re.get() * re.get();
			} catch (Exception e) {
			}
			System.out.println(myRe);
		}

	}
}
